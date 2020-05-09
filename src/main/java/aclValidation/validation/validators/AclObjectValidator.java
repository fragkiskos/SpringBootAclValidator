package aclValidation.validation.validators;

import aclValidation.validation.aclProviding.IAclValidator;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;

import javax.servlet.http.HttpServletRequest;
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
