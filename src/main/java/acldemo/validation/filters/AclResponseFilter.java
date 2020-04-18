package acldemo.validation.filters;

import acldemo.validation.aclAnnotations.AclResponseValidate;
import acldemo.validation.annotationInfoExtraction.response.AclResponseValidationInfoExtractor;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.valueExtractorProviders.ResponseValueExtractorProvider;
import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.annotationInfoExtraction.AclValidationInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AclResponseFilter {
    public boolean filter(HttpServletResponse response, Object handler) throws IdMapperLoadingException, GetIdInvocationFailException, UnSupportedMappingException {
        ResponseValueExtractorProvider responseValueExtractorProvider = new ResponseValueExtractorProvider(response);
        List<AclValidationInfo> aclValidations = new AclResponseValidationInfoExtractor()
                .extractInfo(AclResponseValidate.class,handler, responseValueExtractorProvider);
        if(aclValidations.isEmpty()){
            return true;
        }else{
            return validateAcl(aclValidations,"frank from jwt");
        }
    }

    private boolean validateAcl(List<AclValidationInfo> aclValidations, String actor) {
        aclValidations.forEach(v->{
            v.getIds().forEach(id-> System.out.println(
                    "Response: I have to check if  "+actor+" can "+v.getAction()+" object id:"+ id + " of class "+v.getClassname())
            );
        });
        return true;
    }
}
