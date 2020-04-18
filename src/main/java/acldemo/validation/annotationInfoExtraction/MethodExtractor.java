package acldemo.validation.annotationInfoExtraction;

import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

public class MethodExtractor {
    public Method getMethod(HandlerMethod handler) {
        HandlerMethod hm;
        hm = handler;
        return hm.getMethod();
    }
}
