package acldemo.restControllers;

import acldemo.validation.aclAnnotations.AclAction;
import acldemo.validation.aclAnnotations.AclRequestValidate;
import acldemo.validation.aclAnnotations.AclResponseValidate;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class DummyController {

    @GetMapping("/longValidation/{store_Id}/babis/{resource_Id}")
    @AclRequestValidate(paramName = "storeId", className = DummyEntity.class, action = AclAction.READ)
    @AclRequestValidate(paramName = "resourceId", className = DummyEntity.class, action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class,action = AclAction.READ)
    public Long longValidation(@PathVariable(value = "store_Id") Long storeId,@PathVariable(value = "resource_Id") Long resourceId){
        return storeId;
    }

    @GetMapping("/stringValidation/{a}/singleObjectParams")
    @AclRequestValidate(paramName = "b", className = DummyEntity.class  , action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class,action = AclAction.READ)
    public String stringValidation(@PathVariable(value = "a") String id, @RequestParam(name="a") Long b ){
        return String.valueOf(id);
    }

    @GetMapping("/listOfLongValidation/{a}/listObjectParams")
    @AclRequestValidate(paramName = "listOfB", className = DummyEntity.class  , action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class, action = AclAction.READ)
    public List<Long> listOfLongValidation(@PathVariable(value = "a") Long id, @RequestParam(name="a") List<Long> listOfB ){
        return listOfB;
    }

    @GetMapping("/listOfStringValidation/{a}/listObjectParams")
    @AclRequestValidate(paramName = "listOfB", className = DummyEntity.class  , action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class, action = AclAction.READ)
    public List<String> listOfStringValidation(@PathVariable(value = "a") Long id, @RequestParam(name="a") List<String> listOfB ){
        return listOfB;
    }

    ////Post

    @PostMapping("customObjectValidation/test")
    @AclRequestValidate(paramName = "userDto", className = DummyEntity.class  , action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class, action = AclAction.READ )
    public UserDto customObjectValidation(@RequestBody UserDto userDto){
        return userDto;
    }

    @PostMapping("postExampleCustomMapping/testCustomMapper")
    @AclRequestValidate(paramName = "userDto", className = UserDto.class  , action = AclAction.READ,idMapper = UserDtoIdMapper.class)
    @AclResponseValidate(className = UserDto.class, action = AclAction.READ)
    public ResponseEntity<UserDto> postExampleCustomMapping(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("post/listOfCustomObjectsValidation/multi")
    @AclRequestValidate(paramName = "userDtos", className = DummyEntity.class  , action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class, action = AclAction.READ)
    public ResponseEntity<List<UserDto>> listOfCustomObjectsValidation(@RequestBody List<UserDto> userDtos){
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping("post/setOfCustomObjectsValidation/multi")
    @AclRequestValidate(paramName = "userDtos", className = DummyEntity.class  , action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class, action = AclAction.READ)
    public ResponseEntity<Set<UserDto>> setOfCustomObjectsValidation(@RequestBody Set<UserDto> userDtos){
        return ResponseEntity.ok(userDtos);
    }

    @PostMapping("post/test/multiString")
    @AclRequestValidate(paramName = "strings", className = DummyEntity.class  , action = AclAction.READ)
    @AclResponseValidate(className = DummyEntity.class,action = AclAction.READ)
    public List<String> postStringsExample(@RequestBody List<String> strings){
        return strings;
    }

}


