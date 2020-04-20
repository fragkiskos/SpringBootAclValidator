package acldemo.validation.request.parameterInfoExtraction;

import acldemo.restControllers.DummyEntity;
import acldemo.restControllers.UserDto;
import acldemo.validation.BodyWrapping.request.CachedBodyHttpServletRequest;
import acldemo.validation.aclAnnotations.AclAction;
import acldemo.validation.aclAnnotations.AclRequestValidate;
import acldemo.validation.annotationInfoExtraction.AclValidationInfo;
import acldemo.validation.annotationInfoExtraction.request.AclRequestValidationInfoExtractor;
import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.exceptions.ParameterNotFoundException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import acldemo.validation.request.parameterInfoExtraction.requestResponseMocking.HttpMockedRequest;
import acldemo.validation.request.parameterInfoExtraction.validatedClasses.PathParamsValidationClass;
import acldemo.validation.request.parameterInfoExtraction.validatedClasses.RequestBodyValidationClass;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestBodyValidationTest {

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
            throws IOException, IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException,
            UnSupportedMappingException, ClassNotFoundException, NoSuchMethodException {
        HttpMockedRequest httpMockedRequest = new HttpMockedRequest();
        InputStream inputStream = new ByteArrayInputStream(body.getBytes());
        httpMockedRequest.setInputStream(inputStream);
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpMockedRequest);

        RequestValueExtractorProvider requestValueExtractorProvider = new RequestValueExtractorProvider(cachedRequest);
        List<AclValidationInfo> aclValidations = new AclRequestValidationInfoExtractor()
                .extractInfo(AclRequestValidate.class,
                        getHandlerMethod(methodName,parameterType),
                        requestValueExtractorProvider);
        return aclValidations;
    }

    private HandlerMethod getHandlerMethod(String methodName, Class parameterType) throws ClassNotFoundException, NoSuchMethodException {
        Class myClass = Class.forName(RequestBodyValidationClass.class.getName());
        Method method = myClass.getMethod(methodName, parameterType);
        HandlerMethod hm = new HandlerMethod(new String(""),method);
        return hm;
    }
}
