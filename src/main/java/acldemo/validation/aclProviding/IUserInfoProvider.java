package acldemo.validation.aclProviding;

import javax.servlet.http.HttpServletRequest;

public interface IUserInfoProvider {

    UserInfo getUserInfo(HttpServletRequest request);
}
