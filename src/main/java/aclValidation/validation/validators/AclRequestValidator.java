package aclValidation.validation.validators;

import aclValidation.validation.aclProviding.*;
import aclValidation.validation.aclAnnotations.AclRequestValidate;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.annotationInfoExtraction.request.AclRequestValidationInfoExtractor;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.ParameterNotFoundException;
import aclValidation.validation.userinfoProviding.IUserInfoProvider;
import aclValidation.validation.userinfoProviding.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


public class AclRequestValidator {

    IAclValidator aclProvider;

    public AclRequestValidator(IAclValidator aclProvider) {
        this.aclProvider = aclProvider;
    }

    public boolean validate(HttpServletRequest request, Object handler) throws IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException {

        RequestValueExtractorProvider requestValueExtractorProvider = new RequestValueExtractorProvider(request);
        List<AclValidationInfo> aclValidations = new AclRequestValidationInfoExtractor().extractInfo(AclRequestValidate.class,handler, requestValueExtractorProvider);
        if(aclValidations.isEmpty()){
            return true;
        }else{
            return validateAcl(aclValidations,request);
        }
    }

    private boolean validateAcl(List<AclValidationInfo> aclValidations,HttpServletRequest request) {
        for(AclValidationInfo aclValidation:aclValidations){
            if(!aclProvider.validate(aclValidation,request)) return false;
        }
        return true;
    }


}
