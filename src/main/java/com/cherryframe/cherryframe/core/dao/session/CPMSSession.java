package com.cherryframe.cherryframe.core.dao.session;

import java.util.Date;
import java.util.HashMap;

import static com.cherryframe.cherryframe.core.constants.CPMSCoreConstants.USER_SESSION_KEY;

public final class CPMSSession {

    private static final HashMap<String, Object> session = new HashMap<>();

    public static HashMap<String, Object> getCurrentSession() {
        return session;
    }

    public static HashMap<String, Object> getOrCreateSession(final String userUID, final String userWorkspace) {
        if (session.isEmpty())
        {
            session.put(USER_SESSION_KEY, CPMSUserSession.getCurrentUserSession(userUID, userWorkspace));
            session.put("LOGIN_TIME", new Date());
        }
        return session;
    }

    public static void cleanupSession() {
        if (!session.isEmpty()) {
            CPMSUserSession.cleanCurrentUserSession();
            session.clear();
        }
    }
}
