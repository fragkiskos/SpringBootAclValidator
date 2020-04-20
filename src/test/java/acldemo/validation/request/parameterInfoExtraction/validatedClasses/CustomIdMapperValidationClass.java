package acldemo.validation.request.parameterInfoExtraction.validatedClasses;

import acldemo.restControllers.UserDto;
import acldemo.restControllers.UserDtoIdMapper;
import acldemo.validation.aclAnnotations.AclAction;
import acldemo.validation.aclAnnotations.AclRequestValidate;
import acldemo.validation.aclAnnotations.AclResponseValidate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public class CustomIdMapperValidationClass {

    @AclRequestValidate(paramName = "userDto", className = UserDto.class  , action = AclAction.READ,idMapper = UserDtoIdMapper.class)
    @AclResponseValidate(className = UserDto.class, action = AclAction.READ)
    public ResponseEntity<UserDto> customObjectValidationWithCustom(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userDto);
    }
}
