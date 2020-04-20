package acldemo.validation.request.parameterInfoExtraction;

import acldemo.restControllers.DummyEntity;
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
import acldemo.validation.request.parameterInfoExtraction.validatedClasses.RequestParamsValidationClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestParamsValidationTest {

    @Test
    public void shouldValidateLong() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {

        Map parameterMap = new HashMap<String,String[]>();
        parameterMap.put("storeId",new String[]{"1"});
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(parameterMap,"longValidation",Long.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidatelistOfLongs() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {

        Map parameterMap = new HashMap<String,String[]>();
        parameterMap.put("listOfB",new String[]{"1,2,3"});
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(parameterMap,"listOfLongValidation",List.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(3,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(new Long(2),aclValidations.get(0).getIds().get(1));
        Assert.assertEquals(new Long(3),aclValidations.get(0).getIds().get(2));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateString() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {

        Map parameterMap = new HashMap<String,String[]>();
        parameterMap.put("b",new String[]{"1"});
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(parameterMap,"stringValidation", String.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidatelistOfStrings() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {

        Map parameterMap = new HashMap<String,String[]>();
        parameterMap.put("listOfB",new String[]{"1,2,3"});
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(parameterMap,"listOfStringValidation",List.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(3,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(new Long(2),aclValidations.get(0).getIds().get(1));
        Assert.assertEquals(new Long(3),aclValidations.get(0).getIds().get(2));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    private List<AclValidationInfo> prepareRequestAndValidateMethod(Map parameterMap,String methodName, Class parameterType) throws IOException, IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ClassNotFoundException, NoSuchMethodException {
        HttpMockedRequest httpMockedRequest = new HttpMockedRequest();
        httpMockedRequest.setParameterMap(parameterMap);
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpMockedRequest);
        RequestValueExtractorProvider requestValueExtractorProvider = new RequestValueExtractorProvider(cachedRequest);
        List<AclValidationInfo> aclValidations = new AclRequestValidationInfoExtractor()
                .extractInfo(AclRequestValidate.class,
                        getHandlerMethod(methodName,parameterType),
                        requestValueExtractorProvider);
        return aclValidations;
    }

    private HandlerMethod getHandlerMethod(String methodName, Class parameterType) throws ClassNotFoundException, NoSuchMethodException {
        Class myClass = Class.forName(RequestParamsValidationClass.class.getName());
        Method method = myClass.getMethod(methodName, parameterType);
        HandlerMethod hm = new HandlerMethod(new String(""),method);
        return hm;
    }
}
