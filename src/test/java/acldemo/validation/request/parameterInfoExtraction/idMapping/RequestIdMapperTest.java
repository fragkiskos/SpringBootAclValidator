package acldemo.validation.request.parameterInfoExtraction.idMapping;


import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.mapping.RequestIdMapper;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;
import acldemo.restControllers.UserDto;
import acldemo.restControllers.UserDtoIdMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestIdMapperTest {

    @Test
    public void shouldFindIdLongParameter() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getLongParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "1", Optional.empty());

        Assert.assertEquals(longs.size(),1);
        Assert.assertEquals(longs.get(0),new Long(1));
    }

    @Test
    public void shouldFindIdStringParameter() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getStringParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "1", Optional.empty());

        Assert.assertEquals(longs.size(),1);
        Assert.assertEquals(longs.get(0),new Long(1));
    }

    @Test
    public void shouldFindIdJsonObject() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getStringParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "{id:1}", Optional.empty());

        Assert.assertEquals(longs.size(),1);
        Assert.assertEquals(longs.get(0),new Long(1));
    }

    @Test
    public void shouldFindIdCustomObject() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getCustomObjectParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "{'id':1,'name':'frank'}", Optional.empty());

        Assert.assertEquals(longs.size(),1);
        Assert.assertEquals(longs.get(0),new Long(1));
    }


    @Test
    public void shouldFindIdCustomObjectWithCustomMapper() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getCustomObjectParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "{'id':1,'name':'frank'}", Optional.of(new UserDtoIdMapper()));

        Assert.assertEquals(longs.size(),1);
        Assert.assertEquals(longs.get(0),new Long(1));
    }

    @Test
    public void shouldFindIdsInListofLongs() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getListofLongsParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "1,2,3,4", Optional.empty());

        Assert.assertEquals(longs.size(),4);
        Assert.assertEquals(longs.get(0),new Long(1));
        Assert.assertEquals(longs.get(1),new Long(2));
        Assert.assertEquals(longs.get(2),new Long(3));
        Assert.assertEquals(longs.get(3),new Long(4));
    }

    @Test
    public void shouldFindIdsInListofStrings() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getListofStringsParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "1,2,3,4", Optional.empty());

        Assert.assertEquals(longs.size(),4);
        Assert.assertEquals(longs.get(0),new Long(1));
        Assert.assertEquals(longs.get(1),new Long(2));
        Assert.assertEquals(longs.get(2),new Long(3));
        Assert.assertEquals(longs.get(3),new Long(4));
    }

    @Test
    public void shouldFindIdsInListofCustomObjects() throws GetIdInvocationFailException, UnSupportedMappingException {
        RequestIdMapper mapper = new RequestIdMapper();
        Parameter p = getListofCustomObjParameter();
        final List<Long> longs = mapper.mapToLongs(getParamType(p), "[" +
                "{'id':5,'name':'frank'}," +
                "{'id':6,'name':'stergios'}," +
                "{'id':7,'name':'babis'}" +
                "]", Optional.empty());

        Assert.assertEquals(longs.size(),3);
        Assert.assertEquals(longs.get(0),new Long(5));
        Assert.assertEquals(longs.get(1),new Long(6));
        Assert.assertEquals(longs.get(2),new Long(7));
    }


    public void parameterProvider(String type1){}
    public void parameterProvider(Long type1){}
    public void parameterProvider(UserDto type1){}
    public void parameterProvider(List<Long> type1){}
    public void parameterProviderListStrings(List<String> type1){}
    public void parameterProviderListOfCustomObj(List<UserDto> type1){}
    Parameter getStringParameter(){
        try {
            Parameter parameter = RequestIdMapperTest.class
                    .getMethod("parameterProvider", String.class)
                    .getParameters()[0];
            return parameter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Parameter getLongParameter(){
        try {
            Parameter parameter = RequestIdMapperTest.class
                    .getMethod("parameterProvider", Long.class)
                    .getParameters()[0];
            return parameter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Parameter getCustomObjectParameter(){
        try {
            Parameter parameter = RequestIdMapperTest.class
                    .getMethod("parameterProvider", UserDto.class)
                    .getParameters()[0];
            return parameter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Parameter getListofLongsParameter(){
        try {
            Parameter parameter = RequestIdMapperTest.class
                    .getMethod("parameterProvider", List.class)
                    .getParameters()[0];
            return parameter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Parameter getListofStringsParameter(){
        try {
            Parameter parameter = RequestIdMapperTest.class
                    .getMethod("parameterProviderListStrings", List.class)
                    .getParameters()[0];
            return parameter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Parameter getListofCustomObjParameter(){
        try {
            Parameter parameter = RequestIdMapperTest.class
                    .getMethod("parameterProviderListOfCustomObj", List.class)
                    .getParameters()[0];
            return parameter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ParamType getParamType(Parameter p){
        if(p.getType().equals(List.class)){
            return new ParamType(p.getType(),(ParameterizedType)p.getParameterizedType());
        }else{
            return new ParamType(p.getType());
        }
    }

}