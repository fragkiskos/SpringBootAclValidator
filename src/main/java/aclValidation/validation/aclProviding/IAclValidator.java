package aclValidation.validation.aclProviding;


import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface IAclValidator {
    boolean validate(AclValidationInfo aclValidation, HttpServletRequest request);
    boolean validate(Set<String> roles, HttpServletRequest request);
    boolean isUserSysAdmin(HttpServletRequest request);
}
