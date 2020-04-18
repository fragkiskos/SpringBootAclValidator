package acldemo.validation;


import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.exceptions.ParameterNotFoundException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.filters.AclRequestFilter;
import acldemo.validation.filters.AclResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AclRequestInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(AclRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            AclRequestFilter aclRequestFilter = new AclRequestFilter();
            return aclRequestFilter.filter(request, handler);
        } catch (IdMapperLoadingException e) {
            //se deuteri fasi tha prepei na ginw austiros kai na faei 401
            logger.error(e.getMessage());
            return HandlerInterceptor.super.preHandle(request, response, handler);
        } catch (ParameterNotFoundException e) {
            logger.error(e.getMessage());
            return HandlerInterceptor.super.preHandle(request, response, handler);
        } catch (GetIdInvocationFailException e) {
            logger.error(e.getMessage());
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }catch (UnSupportedMappingException e){
            logger.error(e.getMessage());
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        try{
            AclResponseFilter aclResponseFilter = new AclResponseFilter();
            aclResponseFilter.filter(response,handler);
        } catch (IdMapperLoadingException e) {
            //se deuteri fasi tha prepei na ginw austiros kai na faei 401
            logger.error(e.getMessage());
        } catch (GetIdInvocationFailException e) {
            logger.error(e.getMessage());
        }catch (UnSupportedMappingException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("after post");
    }

}
