package acldemo.validation.request.parameterInfoExtraction.requestResponseMocking;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpMockedRequest extends TestRequest {

    Map parameterMap = new HashMap<String,String[]>();

    Map pathVariables;

    InputStream inputStream;

    public HttpMockedRequest() {
        this.inputStream = new ByteArrayInputStream("body".getBytes());
    }

    public void setParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
    }

    public void setPathVariables(Map pathVariables) {
        this.pathVariables = pathVariables;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    @Override
    public Object getAttribute(String var1){
        return pathVariables;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream servletInputStream=new ServletInputStream(){
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            public int read() throws IOException {
                return inputStream.read();
            }
        };
        return servletInputStream;
    }

}
