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

public class AclResponseValidator {
    IAclValidator aclValidator;
    HttpServletRequest request;

    public AclResponseValidator(IAclValidator aclValidator, HttpServletRequest request) {
        this.aclValidator = aclValidator;
        this.request = request;
    }

    public boolean validate(HttpServletResponse response, Object handler) throws IdMapperLoadingException, GetIdInvocationFailException, UnSupportedMappingException {
        return validateObjectAcl(response, handler);
    }

    private boolean validateObjectAcl(HttpServletResponse response, Object handler) throws IdMapperLoadingException, GetIdInvocationFailException, UnSupportedMappingException {
        ResponseValueExtractorProvider responseValueExtractorProvider = new ResponseValueExtractorProvider(response);
        List<AclValidationInfo> aclValidations = new AclResponseValidationInfoExtractor()
                .extractInfo(handler, responseValueExtractorProvider);
        return new AclObjectValidator(aclValidator, request).validateAcls(aclValidations);
    }
}
