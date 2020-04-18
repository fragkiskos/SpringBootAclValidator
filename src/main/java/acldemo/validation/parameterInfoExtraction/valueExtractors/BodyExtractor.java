package acldemo.validation.parameterInfoExtraction.valueExtractors;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.mapping.IdMapper;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;
import acldemo.validation.mapping.CustomIdMapper;

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
