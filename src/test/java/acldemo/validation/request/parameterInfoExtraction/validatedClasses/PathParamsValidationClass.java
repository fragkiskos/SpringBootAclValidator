package acldemo.validation.request.parameterInfoExtraction.validatedClasses;

import acldemo.restControllers.DummyEntity;
import acldemo.validation.aclAnnotations.AclAction;
import acldemo.validation.aclAnnotations.AclRequestValidate;
import org.springframework.web.bind.annotation.PathVariable;

public class PathParamsValidationClass {

    @AclRequestValidate(paramName = "storeId", className = DummyEntity.class, action = AclAction.READ)
    public Long longValidation(@PathVariable(value = "storeId") Long storeId){
        return storeId;
    }

    @AclRequestValidate(paramName = "b", className = DummyEntity.class  , action = AclAction.READ)
    public String stringValidation(@PathVariable(value="b") String b ){
        return b;
    }
}
