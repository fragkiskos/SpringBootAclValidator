package aclValidation.validation.request.parameterInfoExtraction.validatedClasses;

import aclValidation.restControllers.UserDto;
import aclValidation.restControllers.UserDtoIdMapper;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclRequestValidate;
import aclValidation.validation.aclAnnotations.AclResponseValidate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public class CustomIdMapperValidationClass {

    @AclRequestValidate(paramName = "userDto", className = UserDto.class  , action = AclAction.READ,idMapper = UserDtoIdMapper.class)
    @AclResponseValidate(className = UserDto.class, action = AclAction.READ)
    public ResponseEntity<UserDto> customObjectValidationWithCustom(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userDto);
    }
}
