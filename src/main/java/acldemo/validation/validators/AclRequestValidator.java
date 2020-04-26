package acldemo.validation.validators;

import acldemo.validation.aclProviding.*;
import acldemo.validation.aclAnnotations.AclRequestValidate;
import acldemo.validation.annotationInfoExtraction.AclValidationInfo;
import acldemo.validation.annotationInfoExtraction.request.AclRequestValidationInfoExtractor;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.valueExtractorProviders.RequestValueExtractorProvider;
import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.exceptions.ParameterNotFoundException;
import acldemo.validation.userinfoProviding.IUserInfoProvider;
import acldemo.validation.userinfoProviding.UserInfo;

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
                        aclValidation.getAction());


                Optional<Long> aclOpt = aclProvider.find(searchCriteria);
                if(!aclOpt.isPresent()) return false;
            }
        }
        return true;
    }


}
