package aclValidation.validation.request.parameterInfoExtraction.validatedClasses;

import aclValidation.restControllers.DummyEntity;
import aclValidation.restControllers.UserDto;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclResponseValidate;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public class ResponseBodyValidationClass {
    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public Long longValidation(@RequestBody Long a){
        return a;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public List<Long> listOfLongValidation(@RequestBody List<Long> ids){
        return ids;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public String stringValidation(@RequestBody String a){
        return a;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public List<String> listOfStringValidation(@RequestBody List<String> ids){
        return ids;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public String stringJsonObjectValidation(@RequestBody String a){
        return a;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public String listOfStringJsonObjectsValidation(@RequestBody String ids){
        return ids;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public UserDto customObjectValidation(@RequestBody UserDto user){
        return user;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public List<UserDto> listOfCustomObjectsValidation(@RequestBody List<UserDto> users){
        return users;
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public Map mapValidation(@RequestBody Map map){
        return map;
    }
}
