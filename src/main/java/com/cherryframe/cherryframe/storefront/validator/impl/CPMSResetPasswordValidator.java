package com.cherryframe.cherryframe.storefront.validator.impl;

import com.cherryframe.cherryframe.storefront.form.ResetPasswordForm;
import com.cherryframe.cherryframe.storefront.validator.CPMSValidator;

public class CPMSResetPasswordValidator implements CPMSValidator<ResetPasswordForm> {
    @Override
    public boolean validate(ResetPasswordForm form) {
        return form.getNewPass().contentEquals(form.getRepeatedNewPass());
    }
}
