package acldemo.validation.annotationInfoExtraction.request;

import acldemo.validation.aclAnnotations.AclAction;
import acldemo.validation.aclAnnotations.AclRequestValidate;
import acldemo.validation.annotationInfoExtraction.AclValidationInfo;
import acldemo.validation.annotationInfoExtraction.CustomMapperLoader;
import acldemo.validation.annotationInfoExtraction.MethodExtractor;
import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.exceptions.ParameterNotFoundException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import acldemo.validation.parameterInfoExtraction.valueExtractors.ParameterValueExtractor;
import acldemo.validation.mapping.CustomIdMapper;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AclRequestValidationInfoExtractor {

    public List<AclValidationInfo> extractInfo(Class annotation,
                                               Object handler,
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
            results.add(new AclValidationInfo(ids,entityName,action));
        }



        return results;
    }


}
