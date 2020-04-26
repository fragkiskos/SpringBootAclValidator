package aclValidation.validation.request.parameterInfoExtraction.validatedClasses;

import aclValidation.restControllers.DummyEntity;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclRequestValidate;
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
