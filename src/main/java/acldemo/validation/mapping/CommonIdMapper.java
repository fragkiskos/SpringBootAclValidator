package acldemo.validation.mapping;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;
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
            if(pType.toString().equals("java.util.List<java.lang.Long>")
                    || pType.toString().equals("java.util.List<java.lang.String>")){
                return extractIdsFromPlainList((String) argValue);
            }
            Collection<Object> objects = gson.fromJson((String) argValue, pType);
            List<Long> ids = new ArrayList<>();
            for(Object o: objects){
                ids.add(extractSingleObjectIds(o.getClass(),o));
            }
            return ids;
        }
        return Collections.singletonList(extractSingleObjectIds(paramType.getType(),argValue));


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

    private Long extractSingleObjectIds(Class objectClass, Object argValue) throws GetIdInvocationFailException {

        //argvalue is long
        if(objectClass.equals(Long.class)) return Long.parseLong((String)argValue);

        //string mapping
        if(objectClass.equals(String.class)){
            Optional<Long> id = stringMapping(objectClass, argValue);
            if(id.isPresent()) return id.get();
        }


        //custom object mapping
        //if object class is a custom class but argvalue is still String
        //it is json and we need to tranform it to object
        Object o = argValue;
        if(argValue.getClass().equals(String.class)){
            o = gson.fromJson((String)argValue,objectClass);
        }
        return getIdFromObject(o);
    }

    private Optional<Long> stringMapping(Class objectClass, Object argValue) {
        if(objectClass.equals(String.class)){
            try{
                return Optional.of(Long.parseLong((String) argValue));
            }catch (NumberFormatException ex){
                JsonObject jsonObject = new JsonParser().parse((String) argValue).getAsJsonObject();
                return parseJsonObjectForId(jsonObject);
            }
        }
        return Optional.empty();
    }


    private Optional<Long> parseJsonObjectForId(JsonObject jsonObject) {
        Long id = jsonObject.get("id").getAsLong();
        if(id == null) {
            return Optional.empty();
        }else {
            return Optional.of(id);
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
