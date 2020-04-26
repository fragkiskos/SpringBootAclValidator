package aclValidation.validation.request.parameterInfoExtraction.requestResponseMocking;

import aclValidation.validation.BodyWrapping.response.CachedBodyHttpServletResponse;
import java.io.IOException;

public class MockedHttpResponse extends CachedBodyHttpServletResponse {

    String body;

    public MockedHttpResponse(String body) throws IOException {
        super(new TestResponse());
        this.body = body;
    }

    @Override
    public byte[] getContentAsByteArray() {
        return body.getBytes();
    }
}
