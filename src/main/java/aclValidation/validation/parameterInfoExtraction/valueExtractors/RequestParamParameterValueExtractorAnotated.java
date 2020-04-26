package aclValidation.validation.parameterInfoExtraction.valueExtractors;

import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.mapping.RequestIdMapper;
import aclValidation.validation.parameterInfoExtraction.typing.ParamType;
import aclValidation.validation.mapping.CustomIdMapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RequestParamParameterValueExtractorAnotated extends RequestParamParameterValueExtractor implements ParameterValueExtractor {

    Annotation annotation;

    public RequestParamParameterValueExtractorAnotated(Annotation annotation,
                                                       ParamType paramType,
                                                       Map requestParameters,
                                                       Optional<CustomIdMapper> userDefineIdMapper,
                                                       String parameName) {
        super(paramType,requestParameters, userDefineIdMapper,parameName);
        this.annotation = annotation;
    }


    @Override
    public List<Long> extractIds() throws GetIdInvocationFailException, UnSupportedMappingException {
        Object requestParamValue = getRequestParamValue(annotation);
        RequestIdMapper requestIdMapper = new RequestIdMapper();
        return requestIdMapper.mapToLongs(this.paramType,requestParamValue,this.userDefineIdMapper);
    }

    private Object getRequestParamValue(Annotation annotation) {
        RequestParam requestParamAnnotation = (RequestParam) annotation;
        String[] values = (String[]) requestParameters.get(requestParamAnnotation.name());
        return values[0];
    }
}
