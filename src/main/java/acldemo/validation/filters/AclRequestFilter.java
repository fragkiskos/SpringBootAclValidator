package acldemo.validation.filters;

import acldemo.validation.aclAnnotations.AclRequestValidate;
import acldemo.validation.annotationInfoExtraction.AclValidationInfo;
import acldemo.validation.annotationInfoExtraction.request.AclRequestValidationInfoExtractor;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.exceptions.ParameterNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AclRequestFilter {
    public boolean filter(HttpServletRequest request, Object handler) throws IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException {
        RequestValueExtractorProvider requestValueExtractorProvider = new RequestValueExtractorProvider(request);
        List<AclValidationInfo> aclValidations = new AclRequestValidationInfoExtractor().extractInfo(AclRequestValidate.class,handler, requestValueExtractorProvider);
        if(aclValidations.isEmpty()){
            return true;
        }else{
            return validateAcl(aclValidations,"frank from jwt");
        }
    }

    private boolean validateAcl(List<AclValidationInfo> aclValidations, String actor) {
        aclValidations.forEach(v->{
            v.getIds().forEach(id-> System.out.println(
                "Request: I have to check if  "+actor+" can "+v.getAction()+" object id:"+ id + " of class "+v.getClassname())
            );
        });
        return true;
    }


}
