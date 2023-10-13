package com.cherryframe.cherryframe.core.service.session.impl;

import com.cherryframe.cherryframe.core.dao.session.CPMSSession;
import com.cherryframe.cherryframe.core.service.session.CPMSSessionService;

import java.util.HashMap;

public class CPMSSessionServiceImpl implements CPMSSessionService {

    @Override
    public void getOrCreateSession(String username, String workspace) {
        CPMSSession.getOrCreateSession(username, workspace);
    }

    @Override
    public HashMap<String, Object> getCurrentSession() {
        return CPMSSession.getCurrentSession();
    }

    @Override
    public void clearSession() {
        CPMSSession.cleanupSession();
    }
}
