package aclValidation.validation.validators;

import aclValidation.validation.aclProviding.IAclValidator;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.annotationInfoExtraction.response.AclResponseValidationInfoExtractor;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.ResponseValueExtractorProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AclObjectValidator {
    IAclValidator aclValidator;
    HttpServletRequest request;

    public AclObjectValidator(IAclValidator aclValidator, HttpServletRequest request) {
        this.aclValidator = aclValidator;
        this.request = request;
    }

    public boolean validateAcls (List<AclValidationInfo> aclValidations) {
        if(aclValidations.isEmpty()){
            return true;
        }else{
            for(AclValidationInfo aclValidation:aclValidations){
                if(!aclValidator.validate(aclValidation,request)) return false;
            }
            return true;
        }
    }

}
