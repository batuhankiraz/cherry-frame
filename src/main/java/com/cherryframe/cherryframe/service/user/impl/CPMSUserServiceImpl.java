package com.cherryframe.cherryframe.service.user.impl;

import com.cherryframe.cherryframe.service.user.CPMSUserService;
import com.cherryframe.cherryframe.storefront.form.LoginForm;

public class CPMSUserServiceImpl implements CPMSUserService {

    @Override
    public boolean login(final LoginForm loginForm) {
        // TODO: connect DB
        // TODO: validate credentials and login
        return true;
    }
}
