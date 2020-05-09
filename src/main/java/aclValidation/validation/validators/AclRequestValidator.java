package aclValidation.validation.validators;

import aclValidation.validation.aclProviding.*;
import aclValidation.validation.annotationInfoExtraction.AclValidationInfo;
import aclValidation.validation.annotationInfoExtraction.request.AclRequestValidationInfoExtractor;
import aclValidation.validation.annotationInfoExtraction.request.RoleRestrictionValidationInfoExtractor;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.ParameterNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


public class AclRequestValidator {

    IAclValidator aclValidator;

    public AclRequestValidator(IAclValidator aclValidator) {
        this.aclValidator = aclValidator;
    }

    public boolean validate(HttpServletRequest request, Object handler) throws IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException {

        if (!validateRoleRestriction(handler, request)) {
            return false;
        }
        return validateObjectAcl(request, handler);
    }

    private boolean validateRoleRestriction(Object handler, HttpServletRequest request) {
        RoleRestrictionValidationInfoExtractor roleRestrictionValidationInfoExtractor = new RoleRestrictionValidationInfoExtractor();
        Set<String> roles = roleRestrictionValidationInfoExtractor.extractRoles(handler);
        if (roles.isEmpty()) return true;
        return aclValidator.validate(roles, request);
    }

    private boolean validateObjectAcl(HttpServletRequest request, Object handler) throws IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException {
        RequestValueExtractorProvider requestValueExtractorProvider = new RequestValueExtractorProvider(request);
        List<AclValidationInfo> aclValidations = new AclRequestValidationInfoExtractor().extractInfo(handler, requestValueExtractorProvider);
        return new AclObjectValidator(aclValidator, request).validateAcls(aclValidations);
    }


}
