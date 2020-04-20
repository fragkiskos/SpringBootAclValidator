package acldemo.validation.parameterInfoExtraction.valueExtractorProviders;

import acldemo.validation.mapping.ResponseIdMapper;
import acldemo.validation.parameterInfoExtraction.typing.TypeCreator;
import acldemo.validation.parameterInfoExtraction.valueExtractors.ParameterValueExtractor;
import acldemo.validation.parameterInfoExtraction.valueExtractors.BodyExtractor;
import acldemo.validation.BodyWrapping.response.CachedBodyHttpServletResponse;
import acldemo.validation.mapping.CustomIdMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

public class ResponseValueExtractorProvider {
    HttpServletResponse response;
    TypeCreator typeCreator = new TypeCreator();

    public ResponseValueExtractorProvider(HttpServletResponse response) {
        this.response = response;
    }


    public ParameterValueExtractor getValueExtractor(Optional<CustomIdMapper> userDefinedIdMapper, Class<?> returnType, Type genericType) throws IOException, ClassNotFoundException {
        ResponseIdMapper responseIdMapper = new ResponseIdMapper();
        BodyExtractor valueExtractor =
                new BodyExtractor(typeCreator.getParamType(returnType,genericType),
                        getResponseBodyValue(response),
                        userDefinedIdMapper,
                        responseIdMapper);
        return valueExtractor;
    }

    private String getResponseBodyValue(HttpServletResponse response) {
        CachedBodyHttpServletResponse cachedResponse = (CachedBodyHttpServletResponse) response;
        String responseBody = new String(cachedResponse.getContentAsByteArray());
        return responseBody;
    }


}
