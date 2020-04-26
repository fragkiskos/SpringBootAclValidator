package aclValidation.validation.userinfoProviding;

import javax.servlet.http.HttpServletRequest;

public interface IUserInfoProvider {

    UserInfo getUserInfo(HttpServletRequest request);
}
