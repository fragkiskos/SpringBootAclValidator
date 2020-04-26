package aclValidation.restControllers;

import aclValidation.validation.mapping.CustomIdMapper;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class UserDtoIdMapper implements CustomIdMapper {

    Gson gson = new Gson();

    @Override
    public List<Long> mapToIds(String value) {
        UserDto user = gson.fromJson(value,UserDto.class);
        return Collections.singletonList(user.getId());
    }
}
