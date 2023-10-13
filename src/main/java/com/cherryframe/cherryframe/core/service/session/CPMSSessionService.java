package com.cherryframe.cherryframe.core.service.session;

import java.util.HashMap;

public interface CPMSSessionService {

    void getOrCreateSession(String username, String workspace);
    HashMap<String, Object> getCurrentSession();
    void clearSession();
}
