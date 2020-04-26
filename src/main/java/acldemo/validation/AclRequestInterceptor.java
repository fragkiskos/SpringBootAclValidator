package acldemo.validation;


import acldemo.validation.aclProviding.IAclProvider;
import acldemo.validation.userinfoProviding.IUserInfoProvider;
import acldemo.validation.exceptions.GetIdInvocationFailException;
import acldemo.validation.exceptions.IdMapperLoadingException;
import acldemo.validation.exceptions.ParameterNotFoundException;
import acldemo.validation.exceptions.UnSupportedMappingException;
import acldemo.validation.validators.AclRequestValidator;
import acldemo.validation.validators.AclResponseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    IAclProvider aclProvider;

    @Autowired(required = false)
    @Qualifier("UserInfoProvider")
    IUserInfoProvider userInfoProvider;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            if (checkForProviders()) return HandlerInterceptor.super.preHandle(request, response, handler);
            AclRequestValidator aclRequestValidator = new AclRequestValidator(aclProvider,userInfoProvider);
            return aclRequestValidator.validate(request, handler);
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
            AclResponseValidator aclResponseValidator = new AclResponseValidator(aclProvider,userInfoProvider,request);
            aclResponseValidator.validate(response,handler);
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
        if(userInfoProvider == null){
            logger.error("You don't have any Component wihich implements IUserInfoProvider," +
                    " hence I can not get user's info in order to validate acls");
            return true;
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("after post");
    }

}
