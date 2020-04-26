package aclValidation.validation.parameterInfoExtraction.valueExtractors;

import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.mapping.RequestIdMapper;
import aclValidation.validation.parameterInfoExtraction.typing.ParamType;
import aclValidation.validation.mapping.CustomIdMapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PathVariableParameterValueExtractor implements ParameterValueExtractor {


    Annotation annotation;
    ParamType paramType;
    Map pathVariables;
    Optional<CustomIdMapper> userDefineIdMapper;

    public PathVariableParameterValueExtractor(Annotation annotation,
                                               ParamType paramType,
                                               Map pathVariables,
                                               Optional<CustomIdMapper> userDefineIdMapper) {
        this.annotation = annotation;
        this.paramType = paramType;
        this.pathVariables = pathVariables;
        this.userDefineIdMapper = userDefineIdMapper;
    }

    @Override
    public List<Long> extractIds() throws GetIdInvocationFailException, UnSupportedMappingException {
        Object pathVariableValue = getPathVariableValue(annotation);
        RequestIdMapper requestIdMapper = new RequestIdMapper();
        return requestIdMapper.mapToLongs(this.paramType,pathVariableValue,userDefineIdMapper);
    }
    private Object getPathVariableValue(Annotation annotation) {
        PathVariable pathVariableAnnotation = (PathVariable) annotation;
        return pathVariables.get(pathVariableAnnotation.value());
    }
}
