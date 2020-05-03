package aclValidation.validation.aclProviding;


import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.userinfoProviding.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface IAclValidator {
    boolean validate(AclValidationInfo aclValidation, HttpServletRequest request);
}
