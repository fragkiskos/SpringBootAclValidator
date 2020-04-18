package acldemo.validation.aclAnnotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AclResponseValidate {

    Class className() ;

    AclAction action();

    Class idMapper() default void.class;
}
