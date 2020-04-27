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

    IAclProvider aclProvider;
    IUserInfoProvider userInfoProvider;

    public AclRequestValidator(IAclProvider aclProvider,IUserInfoProvider userInfoProvider) {
        this.aclProvider = aclProvider;
        this.userInfoProvider = userInfoProvider;
    }

    public boolean validate(HttpServletRequest request, Object handler) throws IdMapperLoadingException, ParameterNotFoundException, GetIdInvocationFailException, UnSupportedMappingException {
        final UserInfo userInfo = userInfoProvider.getUserInfo(request);
        RequestValueExtractorProvider requestValueExtractorProvider = new RequestValueExtractorProvider(request);
        List<AclValidationInfo> aclValidations = new AclRequestValidationInfoExtractor().extractInfo(AclRequestValidate.class,handler, requestValueExtractorProvider);
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
                        aclValidation.getAction(),
                        aclValidation.getOperatorType());


                Optional<Long> aclOpt = aclProvider.find(searchCriteria);
                if(!aclOpt.isPresent()) return false;
            }
        }
        return true;
    }


}
