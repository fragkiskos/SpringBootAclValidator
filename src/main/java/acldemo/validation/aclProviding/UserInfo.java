package acldemo.validation.aclProviding;

import java.util.List;

public class UserInfo {
    String username;
    List<String> roles;
    Long organizationId;

    public UserInfo(String username, List<String> roles, Long organizationId) {
        this.username = username;
        this.roles = roles;
        this.organizationId = organizationId;
    }

    public UserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
