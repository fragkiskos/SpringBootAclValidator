package aclValidation.validation.mapping;

import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.parameterInfoExtraction.typing.ParamType;

import java.util.List;
import java.util.Optional;

public interface IdMapper  {

     List<Long> mapToLongs(ParamType paramType, Object argValue, Optional<CustomIdMapper> userDefinedMapper
    ) throws GetIdInvocationFailException, UnSupportedMappingException;
}
