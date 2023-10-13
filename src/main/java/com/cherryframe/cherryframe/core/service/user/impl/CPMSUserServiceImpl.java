package com.cherryframe.cherryframe.core.service.user.impl;

import com.cherryframe.cherryframe.core.dao.session.CPMSUserSession;
import com.cherryframe.cherryframe.core.dao.strategy.CPMSDatabaseConnectionStrategy;
import com.cherryframe.cherryframe.core.dao.strategy.impl.CPMSDatabaseConnectionStrategyImpl;
import com.cherryframe.cherryframe.core.dao.user.CPMSUserDao;
import com.cherryframe.cherryframe.core.dao.user.impl.CPMSUserDaoImpl;
import com.cherryframe.cherryframe.core.service.session.CPMSSessionService;
import com.cherryframe.cherryframe.core.service.session.impl.CPMSSessionServiceImpl;
import com.cherryframe.cherryframe.core.service.user.CPMSUserService;
import com.cherryframe.cherryframe.storefront.form.LoginForm;

import static com.cherryframe.cherryframe.core.constants.CPMSCoreConstants.USER_SESSION_KEY;

public class CPMSUserServiceImpl implements CPMSUserService {
    private final CPMSDatabaseConnectionStrategy connectionStrategy = new CPMSDatabaseConnectionStrategyImpl();
    private final CPMSUserDao cpmsUserDao = new CPMSUserDaoImpl();
    private final CPMSSessionService cpmsSessionService = new CPMSSessionServiceImpl();


    @Override
    public boolean login(final LoginForm loginForm) {
        final var username = loginForm.getUsername();
        final var password = loginForm.getPassword();
        final var workspace = loginForm.getWorkspace();
        boolean success = false;
        final var connection = connectionStrategy.connect();
        try {
            success = cpmsUserDao.validateUserCredentials(username, password, connection);
            if (success) {
                cpmsSessionService.getOrCreateSession(username, workspace);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            connectionStrategy.closeConnection(connection);
        }
        return success;
    }

    @Override
    public CPMSUserSession getCurrentUser() {
        return (CPMSUserSession) cpmsSessionService.getCurrentSession().get(USER_SESSION_KEY);
    }

    @Override
    public boolean resetPassword(String newPassword) {
        boolean success = false;
        final var connection = connectionStrategy.connect();
        try {
            final var username = getCurrentUser().getUserUID();
            cpmsUserDao.resetPassword(username, newPassword, connection);
            success = true;
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            connectionStrategy.closeConnection(connection);
        }
        return success;
    }
}
