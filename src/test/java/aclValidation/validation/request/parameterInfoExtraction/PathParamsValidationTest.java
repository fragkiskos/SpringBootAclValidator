package aclValidation.validation.request.parameterInfoExtraction;

import aclValidation.restControllers.DummyEntity;
import aclValidation.validation.BodyWrapping.request.CachedBodyHttpServletRequest;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclRequestValidate;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.annotationInfoExtraction.request.AclRequestValidationInfoExtractor;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.ParameterNotFoundException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import aclValidation.validation.request.parameterInfoExtraction.requestResponseMocking.HttpMockedRequest;
import aclValidation.validation.request.parameterInfoExtraction.validatedClasses.PathParamsValidationClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PathParamsValidationTest {

    @Test
    public void shouldValidateLong() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        Map pathVariables = new LinkedHashMap();
        pathVariables.put("storeId","1");
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(pathVariables,"longValidation",Long.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    @Test
    public void shouldValidateString() throws IOException, NoSuchMethodException, ClassNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ParameterNotFoundException, IdMapperLoadingException {
        Map pathVariables = new LinkedHashMap();
        pathVariables.put("b","1");
        List<AclValidationInfo> aclValidations  = prepareRequestAndValidateMethod(pathVariables,"stringValidation",String.class);

        Assert.assertEquals(1,aclValidations.size());
        Assert.assertEquals(1,aclValidations.get(0).getIds().size());
        Assert.assertEquals(new Long(1),aclValidations.get(0).getIds().get(0));
        Assert.assertEquals(DummyEntity.class.getName(),aclValidations.get(0).getClassname());
        Assert.assertEquals(AclAction.READ,aclValidations.get(0).getAction());
    }

    private List<AclValidationInfo> prepareRequestAndValidateMethod(Map pathVariablesMap, String methodName, Class parameterType) throws IOException, IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException, ClassNotFoundException, NoSuchMethodException {
        HttpMockedRequest httpMockedRequest = new HttpMockedRequest();
        httpMockedRequest.setPathVariables(pathVariablesMap);
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpMockedRequest);

        RequestValueExtractorProvider requestValueExtractorProvider = new RequestValueExtractorProvider(cachedRequest);
        List<AclValidationInfo> aclValidations = new AclRequestValidationInfoExtractor()
                .extractInfo(getHandlerMethod(methodName,parameterType),
                        requestValueExtractorProvider);
        return aclValidations;
    }

    private HandlerMethod getHandlerMethod(String methodName, Class parameterType) throws ClassNotFoundException, NoSuchMethodException {
        Class myClass = Class.forName(PathParamsValidationClass.class.getName());
        Method method = myClass.getMethod(methodName, parameterType);
        HandlerMethod hm = new HandlerMethod(new String(""),method);
        return hm;
    }
}
