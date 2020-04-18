package acldemo.validation.annotationInfoExtraction.response;

import acldemo.validation.aclAnnotations.AclAction;
import acldemo.validation.aclAnnotations.AclResponseValidate;
import acldemo.validation.annotationInfoExtraction.CustomMapperLoader;
import acldemo.validation.annotationInfoExtraction.MethodExtractor;
import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.valueExtractorProviders.ResponseValueExtractorProvider;
import acldemo.validation.parameterInfoExtraction.valueExtractors.ParameterValueExtractor;
import acldemo.validation.annotationInfoExtraction.AclValidationInfo;
import acldemo.validation.mapping.CustomIdMapper;
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
            results.add(new AclValidationInfo(ids,entityName,action));
        }
        return results;
    }

}
