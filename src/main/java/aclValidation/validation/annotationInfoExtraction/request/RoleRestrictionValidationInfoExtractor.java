package aclValidation.validation.annotationInfoExtraction.request;

import aclValidation.validation.aclAnnotations.AclRequestValidate;
import aclValidation.validation.aclAnnotations.RoleRestriction;
import aclValidation.validation.annotationInfoExtraction.MethodExtractor;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.*;

public class RoleRestrictionValidationInfoExtractor {

    public Set<String> extractRoles(Object handler){
        Method method = new MethodExtractor().getMethod((HandlerMethod) handler);
        RoleRestriction[] annotations =  method.getDeclaredAnnotationsByType(RoleRestriction.class);
        Set<String> roles = new HashSet<>();
        if(annotations.length == 0) {
            return Collections.EMPTY_SET;
        }

        for(RoleRestriction declaredAnnotation : annotations){
            String[] rolesFromAnnotation = declaredAnnotation.roles();
            for(String role:rolesFromAnnotation){
                roles.add(role);
            }
        }

        return roles;
    }
}
