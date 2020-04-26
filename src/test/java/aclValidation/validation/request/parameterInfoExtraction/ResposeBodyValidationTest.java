package aclValidation.validation.request.parameterInfoExtraction;

import aclValidation.restControllers.DummyEntity;
import aclValidation.restControllers.UserDto;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclResponseValidate;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.annotationInfoExtraction.response.AclResponseValidationInfoExtractor;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.ParameterNotFoundException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.ResponseValueExtractorProvider;
import aclValidation.validation.request.parameterInfoExtraction.requestResponseMocking.MockedHttpResponse;
import aclValidation.validation.request.parameterInfoExtraction.validatedClasses.ResponseBodyValidationClass;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResposeBodyValidationTest {
    Gson gson = new Gson();

    @Test
    public void shouldValidateLong() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        String body = gson.toJson(new Long(1));
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(body,"longValidation",Long.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateListOfLongs() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        List<Long> ids = new ArrayList<>();
        ids.add(new Long(1));
        ids.add(new Long(2));
        ids.add(new Long(3));
        String body = gson.toJson(ids);
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(body,"listOfLongValidation",List.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(3,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(new Long(2),aclValidations.get(0).getIds().get(1));
        Assert.assertEquals(new Long(3),aclValidations.get(0).getIds().get(2));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateString() throws IOException, NoSuchMethodException, ClassNotFoundException,
            GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {

        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod("1","stringValidation",String.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateListOfStrings() throws IOException, NoSuchMethodException, ClassNotFoundException,
            GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        ids.add("3");
        String body = gson.toJson(ids);
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(body,"listOfStringValidation",List.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(3,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(new Long(2),aclValidations.get(0).getIds().get(1));
        Assert.assertEquals(new Long(3),aclValidations.get(0).getIds().get(2));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateJsonObject() throws IOException, NoSuchMethodException, ClassNotFoundException,
            GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        JsonObject jo = new JsonObject();
        jo.addProperty("id","1");
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(jo.toString(),"stringJsonObjectValidation",String.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateListOfJsonObjects()
            throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException,
            UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        JsonArray jarray = new JsonArray();
        JsonObject jo1 = new JsonObject();
        jo1.addProperty("id","1");
        JsonObject jo2 = new JsonObject();
        jo2.addProperty("id","2");
        JsonObject jo3 = new JsonObject();
        jo3.addProperty("id","3");
        jarray.add(jo1);
        jarray.add(jo2);
        jarray.add(jo3);
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(jarray.toString(),
                "listOfStringJsonObjectsValidation",String.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(3,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(new Long(2),aclValidations.get(0).getIds().get(1));
        Assert.assertEquals(new Long(3),aclValidations.get(0).getIds().get(2));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateCustomObject() throws IOException, NoSuchMethodException, ClassNotFoundException,
            GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        UserDto u = new UserDto(new Long(1),"john");
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(gson.toJson(u),"customObjectValidation",UserDto.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateListOfCustomObjects()
            throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException,
            UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        UserDto u1 = new UserDto(new Long(1),"john");
        UserDto u2 = new UserDto(new Long(2),"john");
        UserDto u3 = new UserDto(new Long(3),"john");
        List<UserDto> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(gson.toJson(users),
                "listOfCustomObjectsValidation",List.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(3,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(new Long(2),aclValidations.get(0).getIds().get(1));
        Assert.assertEquals(new Long(3),aclValidations.get(0).getIds().get(2));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateMap() throws IOException, NoSuchMethodException, ClassNotFoundException,
            GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        Map hm = new HashMap<String,String>();
        hm.put("id","1");
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(gson.toJson(hm),"mapValidation",Map.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateMapWithLong() throws IOException, NoSuchMethodException, ClassNotFoundException,
            GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        Map hm = new HashMap<String,String>();
        hm.put("id",1);
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(gson.toJson(hm),"mapValidation",Map.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    private List<AclValidationInfo> prepareRequestAndValidateMethod(String body, String methodName, Class parameterType)
            throws IOException, IdMapperLoadingException, GetIdInvocationFailException,
            UnSupportedMappingException, ClassNotFoundException, NoSuchMethodException {
        MockedHttpResponse httpMockedResponse = new MockedHttpResponse(body);

        ResponseValueExtractorProvider requestValueExtractorProvider = new ResponseValueExtractorProvider(httpMockedResponse);
        List<AclValidationInfo> aclValidations = new AclResponseValidationInfoExtractor()
                .extractInfo(AclResponseValidate.class,
                        getHandlerMethod(methodName,parameterType),
                        requestValueExtractorProvider);
        return aclValidations;
    }

    private HandlerMethod getHandlerMethod(String methodName, Class parameterType) throws ClassNotFoundException, NoSuchMethodException {
        Class myClass = Class.forName(ResponseBodyValidationClass.class.getName());
        Method method = myClass.getMethod(methodName, parameterType);
        HandlerMethod hm = new HandlerMethod(new String(""),method);
        return hm;
    }
}
