package acldemo.validation.request.parameterInfoExtraction.validatedClasses;

import acldemo.restControllers.DummyEntity;
import acldemo.restControllers.UserDto;
import acldemo.restControllers.UserDtoIdMapper;
import acldemo.validation.aclAnnotations.AclAction;
import acldemo.validation.aclAnnotations.AclRequestValidate;
import acldemo.validation.aclAnnotations.AclResponseValidate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

public class RequestParamsValidationClass {

    @AclRequestValidate(paramName = "storeId", className = DummyEntity.class, action = AclAction.READ)
    public Long longValidation(@RequestParam(name = "storeId") Long storeId){
        return storeId;
    }

    @AclRequestValidate(paramName = "b", className = DummyEntity.class  , action = AclAction.READ)
    public String stringValidation(@RequestParam(name="b") String b ){
        return b;
    }

    @AclRequestValidate(paramName = "listOfB", className = DummyEntity.class  , action = AclAction.READ)
    public List<Long> listOfLongValidation(@RequestParam(name="listOfB") List<Long> listOfB ){
        return listOfB;
    }

    @AclRequestValidate(paramName = "listOfB", className = DummyEntity.class  , action = AclAction.READ)
    public List<String> listOfStringValidation( @RequestParam(name="listOfB") List<String> listOfB ){
        return listOfB;
    }


}
