package aclValidation.validation.request.parameterInfoExtraction.validatedClasses;

import aclValidation.restControllers.DummyEntity;
import aclValidation.restControllers.UserDto;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclRequestValidate;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public class RequestBodyValidationClass {
    @AclRequestValidate(paramName = "a", className = DummyEntity.class  , action = AclAction.READ)
    public Long longValidation(@RequestBody Long a){
        return a;
    }

    @AclRequestValidate(paramName = "ids", className = DummyEntity.class  , action = AclAction.READ)
    public List<Long> listOfLongValidation(@RequestBody List<Long> ids){
        return ids;
    }

    @AclRequestValidate(paramName = "a", className = DummyEntity.class  , action = AclAction.READ)
    public String stringValidation(@RequestBody String a){
        return a;
    }

    @AclRequestValidate(paramName = "ids", className = DummyEntity.class  , action = AclAction.READ)
    public List<String> listOfStringValidation(@RequestBody List<String> ids){
        return ids;
    }

    @AclRequestValidate(paramName = "a", className = DummyEntity.class  , action = AclAction.READ)
    public String stringJsonObjectValidation(@RequestBody String a){
        return a;
    }

    @AclRequestValidate(paramName = "ids", className = DummyEntity.class  , action = AclAction.READ)
    public String listOfStringJsonObjectsValidation(@RequestBody String ids){
        return ids;
    }

    @AclRequestValidate(paramName = "user", className = DummyEntity.class  , action = AclAction.READ)
    public UserDto customObjectValidation(@RequestBody UserDto user){
        return user;
    }

    @AclRequestValidate(paramName = "users", className = DummyEntity.class  , action = AclAction.READ)
    public List<UserDto> listOfCustomObjectsValidation(@RequestBody List<UserDto> users){
        return users;
    }

    @AclRequestValidate(paramName = "map", className = DummyEntity.class  , action = AclAction.READ)
    public Map mapValidation(@RequestBody Map map){
        return map;
    }
}
