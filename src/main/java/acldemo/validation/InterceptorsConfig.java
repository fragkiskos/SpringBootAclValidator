package acldemo.validation;

import acldemo.validation.AclRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class InterceptorsConfig extends WebMvcConfigurationSupport {

    @Autowired
    AclRequestInterceptor aclRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(aclRequestInterceptor);
    }
}
