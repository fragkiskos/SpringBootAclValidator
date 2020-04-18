package acldemo.validation.parameterInfoExtraction.valueExtractors;

import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.mapping.RequestIdMapper;
import acldemo.validation.parameterInfoExtraction.typing.ParamType;
import acldemo.validation.mapping.CustomIdMapper;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RequestParamParameterValueExtractor implements ParameterValueExtractor {


    ParamType paramType;
    Map requestParameters;
    Optional<CustomIdMapper> userDefineIdMapper;
    String parameterName;

    public RequestParamParameterValueExtractor(ParamType paramType,
                                               Map requestParameters,
                                               Optional<CustomIdMapper> userDefineIdMapper,
                                               String parameterName ) {
        this.paramType = paramType;
        this.requestParameters = requestParameters;
        this.userDefineIdMapper = userDefineIdMapper;
        this.parameterName = parameterName;
    }


    @Override
    public List<Long> extractIds() throws GetIdInvocationFailException, UnSupportedMappingException {
        Object requestParamValue = requestParameters.get(this.parameterName);
        RequestIdMapper requestIdMapper = new RequestIdMapper();
        return requestIdMapper.mapToLongs(this.paramType,requestParamValue,this.userDefineIdMapper);
    }


    private Object getRequestParamValue(Parameter p) {
        return requestParameters.get(p.getName());
    }



}
