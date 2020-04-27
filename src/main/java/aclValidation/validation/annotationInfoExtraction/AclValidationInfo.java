package aclValidation.validation.annotationInfoExtraction;

import aclValidation.validation.aclAnnotations.AclAction;
import aclValidation.validation.aclAnnotations.OperatorType;

import java.util.List;

public class AclValidationInfo {

    private List<Long> ids;
    private String classname;
    private AclAction action;
    private OperatorType operatorType;

    public AclValidationInfo(List<Long> ids, String classname, AclAction action, OperatorType operatorType) {
        this.ids = ids;
        this.classname = classname;
        this.action = action;
        this.operatorType = operatorType;
    }

    public List<Long> getIds() {
        return ids;
    }

    public String getClassname() {
        return classname;
    }

    public AclAction getAction() {
        return action;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }
}
