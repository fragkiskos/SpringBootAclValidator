package aclValidation.validation;


import aclValidation.validation.aclProviding.IAclValidator;
import aclValidation.validation.exceptions.GetIdInvocationFailException;
import aclValidation.validation.exceptions.IdMapperLoadingException;
import aclValidation.validation.exceptions.ParameterNotFoundException;
import aclValidation.validation.exceptions.UnSupportedMappingException;
import aclValidation.validation.validators.AclRequestValidator;
import aclValidation.validation.validators.AclResponseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AclRequestInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(AclRequestInterceptor.class);

    @Autowired(required = false)
    @Qualifier("AclProvider")
    IAclValidator aclProvider;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(aclProvider.isUserSysAdmin(request)) return true;
        try{
            if (checkForProviders()) return HandlerInterceptor.super.preHandle(request, response, handler);
            AclRequestValidator aclRequestValidator = new AclRequestValidator(aclProvider);
            if(aclRequestValidator.validate(request, handler)){
                return true;
            }else{
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
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
            if (checkForProviders()) return;
            if(aclProvider.isUserSysAdmin(request)) return;
            AclResponseValidator aclResponseValidator = new AclResponseValidator(aclProvider,request);
            if(!aclResponseValidator.validate(response,handler)){
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        } catch (IdMapperLoadingException e) {
            //se deuteri fasi tha prepei na ginw austiros kai na faei 401
            logger.error(e.getMessage());
        } catch (GetIdInvocationFailException e) {
            logger.error(e.getMessage());
        }catch (UnSupportedMappingException e){
            logger.error(e.getMessage());
        }
    }

    private boolean checkForProviders() throws Exception {
        if(aclProvider == null){
            logger.error("You don't have any Component which implements IAclProvider," +
                    " hence I can not get acls in order to validate them");
            return true;
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("after post");
    }

}
