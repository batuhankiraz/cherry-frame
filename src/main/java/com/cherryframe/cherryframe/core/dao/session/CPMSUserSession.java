package com.cherryframe.cherryframe.core.dao.session;

import static java.util.Objects.isNull;

public final class CPMSUserSession {
    private static CPMSUserSession cpmsUserSession;

    private String userUID;
    private String userWorkspace;

    private CPMSUserSession(final String userUID, final String userWorkspace) {
        this.userUID = userUID;
        this.userWorkspace = userWorkspace;
    }

    public static CPMSUserSession getCurrentUserSession(final String userUID, final String userWorkspace) {
        if (isNull(cpmsUserSession)) {
            cpmsUserSession = new CPMSUserSession(userUID, userWorkspace);
        }
        return cpmsUserSession;
    }

    public static void cleanCurrentUserSession() {
        cpmsUserSession = null;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserWorkspace() {
        return userWorkspace;
    }

    public void setUserWorkspace(String userWorkspace) {
        this.userWorkspace = userWorkspace;
    }
}
