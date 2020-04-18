package acldemo.validation.mapping;

import java.util.List;

public interface CustomIdMapper {

    List<Long> mapToIds(String o);
}
