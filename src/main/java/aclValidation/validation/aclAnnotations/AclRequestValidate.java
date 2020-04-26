package aclValidation.validation.aclAnnotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(AclRequestValidates.class)
public @interface AclRequestValidate {

    String paramName();

    Class className() ;

    AclAction action();

    Class idMapper() default void.class;
}
