package aclValidation.validation.validators;

import aclValidation.validation.aclAnnotations.AclResponseValidate;
import aclValidation.validation.aclProviding.AclSearchCriteria;
import aclValidation.validation.aclProviding.IAclProvider;
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
    IAclProvider aclProvider;
    IUserInfoProvider userInfoProvider;
    HttpServletRequest request;

    public AclResponseValidator(IAclProvider aclProvider, IUserInfoProvider userInfoProvider, HttpServletRequest request) {
        this.aclProvider = aclProvider;
        this.userInfoProvider = userInfoProvider;
        this.request = request;
    }

    public boolean validate(HttpServletResponse response, Object handler) throws IdMapperLoadingException, GetIdInvocationFailException, UnSupportedMappingException {
        final UserInfo userInfo = userInfoProvider.getUserInfo(request);
        ResponseValueExtractorProvider responseValueExtractorProvider = new ResponseValueExtractorProvider(response);
        List<AclValidationInfo> aclValidations = new AclResponseValidationInfoExtractor()
                .extractInfo(AclResponseValidate.class,handler, responseValueExtractorProvider);
        if(aclValidations.isEmpty()){
            return true;
        }else{
            return validateAcl(aclValidations,userInfo);
        }
    }

    private boolean validateAcl(List<AclValidationInfo> aclValidations, UserInfo userInfo) {
        for(AclValidationInfo aclValidation:aclValidations){
            for(Long id: aclValidation.getIds()){
                AclSearchCriteria searchCriteria = new AclSearchCriteria(id,
                        aclValidation.getClassname(),
                        userInfo.getUsername(),
                        aclValidation.getAction());


                Optional<Long> aclOpt = aclProvider.find(searchCriteria);
                if(!aclOpt.isPresent()) return false;
            }
        }
        return true;
    }
}
