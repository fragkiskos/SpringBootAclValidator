package aclValidation.validation.aclProviding;

import aclValidation.validation.aclAnnotations.AclAction;


public class AclSearchCriteria {
    protected Long objectId;

    protected String className;

    protected String operator;

    protected AclAction action;

    public AclSearchCriteria(Long objectId, String className, String operator, AclAction action) {
        this.objectId = objectId;
        this.className = className;
        this.operator = operator;
        this.action = action;
    }



    public Long getObjectId() {
        return objectId;
    }


    public String getClassName() {
        return className;
    }

    public String getOperator() {
        return operator;
    }

    public AclAction getAction() {
        return action;
    }
}
