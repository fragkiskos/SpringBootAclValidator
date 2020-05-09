package aclValidation.validation.annotationInfoExtraction.request;

import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclRequestValidate;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.annotationInfoExtraction.CustomMapperLoader;
import aclValidation.validation.annotationInfoExtraction.MethodExtractor;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.ParameterNotFoundException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import aclValidation.validation.parameterInfoExtraction.valueExtractors.ParameterValueExtractor;
import aclValidation.validation.mapping.CustomIdMapper;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AclRequestValidationInfoExtractor {

    public List<AclValidationInfo> extractInfo(Object handler,
                                               RequestValueExtractorProvider requestValueExtractorProvider) throws IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException {
        Method method = new MethodExtractor().getMethod((HandlerMethod) handler);
        List<AclValidationInfo> results = new ArrayList<>();
        AclRequestValidate[] annotations =  method.getDeclaredAnnotationsByType(AclRequestValidate.class);
        if(annotations.length == 0){
            return Collections.EMPTY_LIST;
        }
        for(AclRequestValidate declaredAnnotation : annotations){
            String parameter = declaredAnnotation.paramName();
            Optional<CustomIdMapper> idMapper_opt = new CustomMapperLoader().loadMapper(declaredAnnotation.idMapper());
            ParameterValueExtractor valueExtractor = null;
            try {
                valueExtractor = requestValueExtractorProvider.getValueExtractor(Optional.of(parameter), method.getParameters(), idMapper_opt);
            } catch (IOException e) {
                return Collections.EMPTY_LIST;
            }
            List<Long> ids = valueExtractor.extractIds();
            String entityName = declaredAnnotation.className().getName();
            AclAction action = declaredAnnotation.action();
            results.add(new AclValidationInfo(ids,entityName,action,declaredAnnotation.operatorType()));
        }



        return results;
    }


}
