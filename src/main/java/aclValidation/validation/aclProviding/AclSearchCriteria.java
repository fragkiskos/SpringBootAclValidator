package aclValidation.validation.aclProviding;

import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.OperatorType;


public class AclSearchCriteria {
    protected Long objectId;

    protected String className;

    protected String operator;

    protected AclAction action;

    protected OperatorType operatorType;

    public AclSearchCriteria(Long objectId, String className, String operator, AclAction action, OperatorType operatorType) {
        this.objectId = objectId;
        this.className = className;
        this.operator = operator;
        this.action = action;
        this.operatorType = operatorType;
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

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
