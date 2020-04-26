package aclValidation.validation.request.parameterInfoExtraction.validatedClasses;

import aclValidation.restControllers.DummyEntity;
import aclValidation.restControllers.UserDto;
import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.AclResponseValidate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public class ResponseEntityValidationClass {

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<Long> longValidation(@RequestBody Long a){
        return ResponseEntity.ok(a);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<List<Long>> listOfLongValidation(@RequestBody List<Long> ids){
        return ResponseEntity.ok(ids);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<String> stringValidation(@RequestBody String a){
        return ResponseEntity.ok(a);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<List<String>> listOfStringValidation(@RequestBody List<String> ids){
        return ResponseEntity.ok(ids);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<String> stringJsonObjectValidation(@RequestBody String a){
        return ResponseEntity.ok(a);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<String> listOfStringJsonObjectsValidation(@RequestBody String ids){
        return ResponseEntity.ok(ids);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<UserDto> customObjectValidation(@RequestBody UserDto user){
        return ResponseEntity.ok(user);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<List<UserDto>> listOfCustomObjectsValidation(@RequestBody List<UserDto> users){
        return ResponseEntity.ok(users);
    }

    @AclResponseValidate(className = DummyEntity.class  , action = AclAction.READ)
    public ResponseEntity<Map> mapValidation(@RequestBody Map map){
        return ResponseEntity.ok(map);
    }
}
