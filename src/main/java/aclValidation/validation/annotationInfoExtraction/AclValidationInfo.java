package aclValidation.validation.annotationInfoExtraction;

import aclValidation.validation.aclAnnotations.AclAction;

import java.util.List;

public class AclValidationInfo {

    private List<Long> ids;
    private String classname;
    private AclAction action;

    public AclValidationInfo(List<Long> ids, String classname, AclAction action) {
        this.ids = ids;
        this.classname = classname;
        this.action = action;
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
}
