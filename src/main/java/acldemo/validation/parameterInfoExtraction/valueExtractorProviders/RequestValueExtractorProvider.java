package acldemo.validation.parameterInfoExtraction.valueExtractorProviders;

import acldemo.validation.exceptions.ParameterNotFoundException;
import acldemo.validation.mapping.RequestIdMapper;
import acldemo.validation.parameterInfoExtraction.typing.TypeCreator;
import acldemo.validation.parameterInfoExtraction.valueExtractors.*;
import acldemo.validation.BodyWrapping.request.CachedBodyHttpServletRequest;
import acldemo.validation.mapping.CustomIdMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestValueExtractorProvider {

    HttpServletRequest request;
    TypeCreator typeCreator = new TypeCreator();

    public RequestValueExtractorProvider(HttpServletRequest request) {
        this.request = request;
    }

    public ParameterValueExtractor getValueExtractor(Optional<String> parameterNameOpt,
                                                     Parameter[] parameters,
                                                     Optional<CustomIdMapper> userDefinedIdMapper) throws ParameterNotFoundException, IOException {
        String parameterName = parameterNameOpt.get();
        for (Parameter p : parameters) {
            if(p.getName().equals(parameterName)){
                Annotation[] annotations = p.getAnnotations();
                for(Annotation annotation : annotations ) {
                    if(annotation instanceof PathVariable) {
                        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                        PathVariableParameterValueExtractor valueExtractor =
                                new PathVariableParameterValueExtractor(annotation,
                                        typeCreator.getParamType(p),
                                        pathVariables,
                                        userDefinedIdMapper);
                        return valueExtractor;
                    }else if(annotation instanceof RequestParam) {
                        RequestParamParameterValueExtractorAnotated valueExtractor =
                                new RequestParamParameterValueExtractorAnotated(annotation,typeCreator.getParamType(p),
                                        request.getParameterMap(),
                                        userDefinedIdMapper,
                                        p.getName());
                        return valueExtractor;
                    }else if(annotation instanceof RequestBody){
                        RequestIdMapper requestIdMapper = new RequestIdMapper();
                        BodyExtractor valueExtractor =
                                new BodyExtractor(typeCreator.getParamType(p),
                                        getRequestBodyValue(request),
                                        userDefinedIdMapper,
                                        requestIdMapper);
                        return valueExtractor;
                    }
                }
                RequestParamParameterValueExtractor valueExtractor =
                        new RequestParamParameterValueExtractor(typeCreator.getParamType(p),
                                request.getParameterMap(),
                                userDefinedIdMapper,
                                p.getName());
                return valueExtractor;
            }


        }
        throw new ParameterNotFoundException("parameter"+parameterName+"was not found");
    }


    private String getRequestBodyValue(HttpServletRequest request) throws IOException {
        CachedBodyHttpServletRequest requestWrapper = (CachedBodyHttpServletRequest) request;
        return requestWrapper.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }



}
