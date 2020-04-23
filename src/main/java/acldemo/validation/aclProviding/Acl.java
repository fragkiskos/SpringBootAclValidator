package acldemo.validation.aclProviding;

import acldemo.validation.aclAnnotations.AclAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "acl",
        indexes = {@Index(name = "object_id_index",  columnList="objectId", unique = true),
                @Index(name = "organization_id_index",  columnList="organizationId", unique = true)})
public class Acl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    protected Long objectId;

    @NotNull
    protected Long organizationId;

    @NotNull
    protected String className;

    @NotNull
    protected String operator;

    @NotNull
    protected AclAction action;

    public Acl() {
    }

    public Acl(Long objectId, Long organizationId, String className, String operator, AclAction action) {
        this.objectId = objectId;
        this.className = className;
        this.operator = operator;
        this.action = action;
        this.organizationId = organizationId;
    }

    public Acl(Long objectId, String className, String operator, AclAction action) {
        this.objectId = objectId;
        this.className = className;
        this.operator = operator;
        this.action = action;
        this.organizationId = new Long(-1);
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public AclAction getAction() {
        return action;
    }

    public void setAction(AclAction action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
