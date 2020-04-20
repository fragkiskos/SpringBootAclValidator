package acldemo.validation.mapping;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;
import acldemo.validation.parameterInfoExtraction.typing.TypeCreator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Function;

public class CommonIdMapper {

    Gson gson = new Gson();
    Function<String, List<Long>> plainListParser;

    public CommonIdMapper(Function<String, List<Long>> plainListParser) {
        this.plainListParser = plainListParser;
    }

    public List<Long> mapToLongs(ParamType paramType,
                                 Object argValue,
                                 Optional<CustomIdMapper> userDefinedMapper) throws GetIdInvocationFailException {

        //userDefined mapping
        if(userDefinedMapper.isPresent()) {
            return userDefinedMapper.get().mapToIds((String)argValue);
        }
        


        if(Collection.class.isAssignableFrom(paramType.getType()) ){
            ParameterizedType pType = paramType.getpType();
            TypeCreator typeCreator = new TypeCreator();
            Class<?> collectionIncludedClass;
            try {
                collectionIncludedClass = typeCreator.getClass(pType.getActualTypeArguments()[0].getTypeName());
            } catch (ClassNotFoundException e) {
                throw new GetIdInvocationFailException(e.getMessage());
            }
            if(String.class.isAssignableFrom(collectionIncludedClass)
                    || Long.class.isAssignableFrom(collectionIncludedClass)){
                return extractIdsFromPlainList((String) argValue);
            }
            Collection<Object> objects = gson.fromJson((String) argValue, pType);
            List<Long> ids = new ArrayList<>();
            for(Object o: objects){
                ids.addAll(extractSingleObjectIds(o.getClass(),o));
            }
            return ids;
        }
        return extractSingleObjectIds(paramType.getType(),argValue);


    }

    private List<Long> extractIdsFromPlainList(String value){
        try{
           return  plainListParser.apply(value);
        }catch(NumberFormatException ex){
            return parseListAsJson(value);
        }

    }

    private List<Long> parseListAsJson(String value) {
        List<Long> ids = new ArrayList<>();
        final JsonArray asJsonArray = new JsonParser().parse(value).getAsJsonArray();
        asJsonArray.forEach(j->ids.add(j.getAsLong()));
        return ids;
    }

    private List<Long> parseListOfJsonObjectsAsJson(String value) {
        List<Long> ids = new ArrayList<>();
        final JsonArray asJsonArray = new JsonParser().parse(value).getAsJsonArray();
        asJsonArray.forEach(j->ids.add(j.getAsJsonObject().get("id").getAsLong()));
        return ids;
    }

    private List<Long> extractSingleObjectIds(Class objectClass, Object argValue) throws GetIdInvocationFailException {

        //argvalue is long
        if(objectClass.equals(Long.class)) return Collections.singletonList(Long.parseLong((String)argValue));

        if(objectClass.equals(Map.class)) {
            JsonObject jsonObject = new JsonParser().parse((String) argValue).getAsJsonObject();
            return parseJsonObjectForId(jsonObject);
        }

        //string mapping
        if(objectClass.equals(String.class)){
            return stringMapping(objectClass, argValue);
        }


        //custom object mapping
        //if object class is a custom class but argvalue is still String
        //it is json and we need to tranform it to object
        Object o = argValue;
        if(argValue.getClass().equals(String.class)){
            o = gson.fromJson((String)argValue,objectClass);
        }
        return Collections.singletonList(getIdFromObject(o));
    }

    private List<Long> stringMapping(Class objectClass, Object argValue) {
        if(objectClass.equals(String.class)) {
            try {
                return Collections.singletonList(Long.parseLong((String) argValue));
            } catch (NumberFormatException ex) {
                try {
                    JsonObject jsonObject = new JsonParser().parse((String) argValue).getAsJsonObject();
                    return parseJsonObjectForId(jsonObject);
                } catch (IllegalStateException ilex) {
                    return parseListOfJsonObjectsAsJson((String) argValue);
                }
            }
        }
        return Collections.emptyList();
    }


    private List<Long> parseJsonObjectForId(JsonObject jsonObject) {
        Long id = jsonObject.get("id").getAsLong();
        if(id == null) {
            return Collections.emptyList();
        }else {
            return Collections.singletonList(id);
        }
    }


    private Long getIdFromObject(Object o) throws GetIdInvocationFailException {
        try {
            Method getId = o.getClass().getMethod("getId");
            Long id = (Long) getId.invoke(o);
            return id;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new GetIdInvocationFailException("Method getid() from object"+o.getClass().getName());
        }
    }
}
