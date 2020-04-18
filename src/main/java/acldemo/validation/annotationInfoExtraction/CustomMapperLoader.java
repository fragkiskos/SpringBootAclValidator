package acldemo.validation.annotationInfoExtraction;

import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.mapping.CustomIdMapper;

import java.util.Optional;

public class CustomMapperLoader {

    public Optional<CustomIdMapper> loadMapper(Class idMapperClass) throws IdMapperLoadingException {
        if(idMapperClass.equals(void.class)){
            return Optional.empty();
        }

        try {
            CustomIdMapper mapper = null;
            mapper = (CustomIdMapper) CustomIdMapper.class
                    .getClassLoader()
                    .loadClass(idMapperClass.getName())
                    .newInstance();
            return Optional.of(mapper);
        } catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
            throw new IdMapperLoadingException(idMapperClass+"failed to be loaded");
        }

    }
}
