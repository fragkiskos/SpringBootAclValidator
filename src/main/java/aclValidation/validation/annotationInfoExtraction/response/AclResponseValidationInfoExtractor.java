package aclValidation.validation.annotationInfoExtraction.response;

import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclResponseValidate;
import aclValidation.validation.annotationInfoExtraction.CustomMapperLoader;
import aclValidation.validation.annotationInfoExtraction.MethodExtractor;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.ResponseValueExtractorProvider;
import aclValidation.validation.parameterInfoExtraction.valueExtractors.ParameterValueExtractor;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.mapping.CustomIdMapper;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AclResponseValidationInfoExtractor {

    public List<AclValidationInfo> extractInfo(Class annotation,
                                               Object handler,
                                               ResponseValueExtractorProvider requestValueExtractorProvider) throws IdMapperLoadingException, GetIdInvocationFailException, UnSupportedMappingException {
        Method method = new MethodExtractor().getMethod((HandlerMethod) handler);
        List<AclValidationInfo> results = new ArrayList<>();
        AclResponseValidate[] annotations =  method.getDeclaredAnnotationsByType(AclResponseValidate.class);

        if(annotations.length == 0){
            return Collections.EMPTY_LIST;
        }
        for(AclResponseValidate declaredAnnotation : annotations){
            Optional<CustomIdMapper> idMapper_opt = new CustomMapperLoader().loadMapper(declaredAnnotation.idMapper());
            ParameterValueExtractor valueExtractor = null;
            try {
                valueExtractor = requestValueExtractorProvider.getValueExtractor(idMapper_opt,method.getReturnType(),method.getGenericReturnType());
            } catch (IOException e) {
                return Collections.EMPTY_LIST;
            } catch (ClassNotFoundException e) {
                throw new UnSupportedMappingException();
            }
            List<Long> ids = valueExtractor.extractIds();
            String entityName = declaredAnnotation.className().getName();
            AclAction action = declaredAnnotation.action();
            results.add(new AclValidationInfo(ids,entityName,action,declaredAnnotation.operatorType()));
        }
        return results;
    }

}
