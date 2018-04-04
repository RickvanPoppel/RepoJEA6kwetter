package WebSocket;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionLister {

    private static SessionLister sessionLister = null;
    private static List<String> activeUsers;
    private static Map<String, Session> sessionMap;

    protected SessionLister() {
        activeUsers = new ArrayList<>();
        sessionMap = new HashMap<>();
    }

    public static SessionLister getInstance() {
        if (sessionLister == null) {
            sessionLister = new SessionLister();
        }
        return sessionLister;
    }

    public List<String> getActiveUsers() {
        return activeUsers;
    }

    public Map<String, Session> getSessionMap() {
        return sessionMap;
    }

}
