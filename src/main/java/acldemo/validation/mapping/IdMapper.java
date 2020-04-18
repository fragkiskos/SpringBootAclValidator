package acldemo.validation.mapping;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;

import java.util.List;
import java.util.Optional;

public interface IdMapper  {

    public List<Long> mapToLongs(ParamType paramType,
                                 Object argValue,
                                 Optional<CustomIdMapper> userDefinedMapper
    ) throws GetIdInvocationFailException, UnSupportedMappingException;
}
