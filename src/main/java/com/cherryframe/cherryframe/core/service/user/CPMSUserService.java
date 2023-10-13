package com.cherryframe.cherryframe.core.service.user;

import com.cherryframe.cherryframe.core.dao.session.CPMSUserSession;
import com.cherryframe.cherryframe.storefront.form.LoginForm;

public interface CPMSUserService {

    boolean login(LoginForm loginForm);

    CPMSUserSession getCurrentUser();

    boolean resetPassword(String newPassword);
}
