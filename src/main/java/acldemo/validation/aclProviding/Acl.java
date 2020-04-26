package acldemo.validation.aclProviding;

import acldemo.validation.aclAnnotations.AclAction;



public interface Acl {
    Long getObjectId();
    String getClassName();
    String getOperator();
    AclAction getAction();
    Long getId();
}
