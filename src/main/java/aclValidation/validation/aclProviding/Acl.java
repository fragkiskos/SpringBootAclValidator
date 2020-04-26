package aclValidation.validation.aclProviding;

import aclValidation.validation.aclAnnotations.AclAction;



public interface Acl {
    Long getObjectId();
    String getClassName();
    String getOperator();
    AclAction getAction();
    Long getId();
}
