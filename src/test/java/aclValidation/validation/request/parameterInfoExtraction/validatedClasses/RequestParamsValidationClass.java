package aclValidation.validation.request.parameterInfoExtraction.validatedClasses;

import aclValidation.restControllers.DummyEntity;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclRequestValidate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class RequestParamsValidationClass {

    @AclRequestValidate(paramName = "merchantId", className = DummyEntity.class, action = AclAction.READ)
    public Long longValidation(@RequestParam(name = "merchantId") Long merchantId){
        return merchantId;
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
