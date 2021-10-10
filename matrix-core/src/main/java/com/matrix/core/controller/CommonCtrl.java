package com.matrix.core.controller;

import com.matrix.core.model.security.AuthUserDetails;

public class CommonCtrl extends AbstractCtrl{

    @Override
    protected AuthUserDetails getCurrentUser() {
        return AuthUserDetails.getCurrentUserDetails();
    }
}
