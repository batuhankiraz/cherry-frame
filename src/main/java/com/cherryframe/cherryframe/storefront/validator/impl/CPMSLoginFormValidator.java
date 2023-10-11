package com.cherryframe.cherryframe.storefront.validator.impl;

import com.cherryframe.cherryframe.storefront.form.LoginForm;
import com.cherryframe.cherryframe.storefront.validator.CPMSCherryFrameValidator;

import static java.util.Objects.nonNull;

public class CPMSLoginFormValidator implements CPMSCherryFrameValidator<LoginForm> {

    @Override
    public boolean validate(final LoginForm form){
        return form.getUsername().isEmpty() || form.getPassword().isEmpty() || (nonNull(form.getWorkspace()) && form.getWorkspace().isEmpty());
    }
}
