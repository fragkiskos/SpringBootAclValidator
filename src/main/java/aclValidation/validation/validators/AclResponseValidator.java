package aclValidation.validation.validators;

import aclValidation.validation.aclAnnotations.AclResponseValidate;
import aclValidation.validation.aclProviding.IAclValidator;
import aclValidation.validation.annotationInfoExtraction.response.AclResponseValidationInfoExtractor;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.ResponseValueExtractorProvider;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.userinfoProviding.IUserInfoProvider;
import aclValidation.validation.userinfoProviding.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class AclResponseValidator {
    IAclValidator aclProvider;
    HttpServletRequest request;

    public AclResponseValidator(IAclValidator aclProvider, HttpServletRequest request) {
        this.aclProvider = aclProvider;
        this.request = request;
    }

    public boolean validate(HttpServletResponse response, Object handler) throws IdMapperLoadingException, GetIdInvocationFailException, UnSupportedMappingException {
        ResponseValueExtractorProvider responseValueExtractorProvider = new ResponseValueExtractorProvider(response);
        List<AclValidationInfo> aclValidations = new AclResponseValidationInfoExtractor()
                .extractInfo(AclResponseValidate.class,handler, responseValueExtractorProvider);
        if(aclValidations.isEmpty()){
            return true;
        }else{
            return validateAcl(aclValidations);
        }
    }

    private boolean validateAcl(List<AclValidationInfo> aclValidations) {
        for(AclValidationInfo aclValidation:aclValidations){
            if(!aclProvider.validate(aclValidation,this.request)) return false;
        }
        return true;
    }
}
