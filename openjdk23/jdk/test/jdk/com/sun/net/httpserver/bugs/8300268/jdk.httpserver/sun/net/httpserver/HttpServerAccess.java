/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package sun.net.httpserver;

import com.sun.net.httpserver.HttpServer;
import java.lang.reflect.Field;
import java.util.Set;

public class HttpServerAccess {

   // Given a HttpServer object get the number of idleConnections it currently has
    public static int getIdleConnectionCount(HttpServer server) throws Exception{
        // Use reflection to get server object which is HTTPServerImpl
        Field serverField = server.getClass().getDeclaredField("server");
        serverField.setAccessible(true);

        // Get the actual serverImpl class, then get the IdleConnection Field
        Object serverImpl = serverField.get(server);
        Field idleConnectionField = serverImpl.getClass().getDeclaredField("idleConnections");
        idleConnectionField.setAccessible(true);

        // Finally get the IdleConnection object which is of type Set<HttpConnection>
        Object idleConnectionSet = idleConnectionField.get(serverImpl);
        Set<HttpConnection> idleConnectionPool = (Set<HttpConnection>) idleConnectionSet;
        return idleConnectionPool.size();
    }
}
