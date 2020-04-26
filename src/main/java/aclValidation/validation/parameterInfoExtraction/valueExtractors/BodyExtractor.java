package aclValidation.validation.parameterInfoExtraction.valueExtractors;

import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.mapping.IdMapper;
import aclValidation.validation.parameterInfoExtraction.typing.ParamType;
import aclValidation.validation.mapping.CustomIdMapper;

import java.util.*;

public class BodyExtractor implements ParameterValueExtractor {


    ParamType paramType;
    Optional<CustomIdMapper> userDefineIdMapper;
    String body;
    IdMapper IdMapper;

    public BodyExtractor(ParamType paramType,
                         String body,
                         Optional<CustomIdMapper> userDefineIdMapper,
                         IdMapper IdMapper) {
        this.paramType = paramType;
        this.body = body;
        this.userDefineIdMapper = userDefineIdMapper;
        this.IdMapper = IdMapper;
    }

    @Override
    public List<Long> extractIds() throws GetIdInvocationFailException, UnSupportedMappingException {
        String requestBodyValue = body;
        return IdMapper.mapToLongs(paramType,requestBodyValue,userDefineIdMapper);
    }



}
