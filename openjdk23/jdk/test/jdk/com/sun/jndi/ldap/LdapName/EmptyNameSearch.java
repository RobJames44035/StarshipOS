/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6997561
 * @summary A request for better error handling in JNDI
 * @library ../lib/ /test/lib
 */

import java.net.Socket;
import java.io.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.util.Collections;
import java.util.Hashtable;
import jdk.test.lib.net.URIBuilder;

public class EmptyNameSearch {

    private static final byte[] bindResponse = {
            0x30, 0x0C, 0x02, 0x01, 0x01, 0x61, 0x07, 0x0A,
            0x01, 0x00, 0x04, 0x00, 0x04, 0x00
    };
    private static final byte[] searchResponse = {
            0x30, 0x0C, 0x02, 0x01, 0x02, 0x65, 0x07, 0x0A,
            0x01, 0x02, 0x04, 0x00, 0x04, 0x00
    };

    public static void main(String[] args) throws Exception {

        // Start the LDAP server
        var ldapServer = new BaseLdapServer() {
            @Override
            protected void handleRequest(Socket socket, LdapMessage request,
                    OutputStream out) throws IOException {
                switch (request.getOperation()) {
                    case BIND_REQUEST:
                        out.write(bindResponse);
                        break;
                    case SEARCH_REQUEST:
                        out.write(searchResponse);
                        break;
                    default:
                        break;
                }
            }
        }.start();
        Thread.sleep(3000);

        // Setup JNDI parameters
        Hashtable<Object, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, URIBuilder.newBuilder()
                .scheme("ldap")
                .loopback()
                .port(ldapServer.getPort())
                .build().toString());

        try (ldapServer) {

            // Create initial context
            System.out.println("Client: connecting...");
            DirContext ctx = new InitialDirContext(env);

            System.out.println("Client: performing search...");
            ctx.search(new LdapName(Collections.emptyList()), "cn=*", null);
            ctx.close();

            // Exit
            throw new RuntimeException();

        } catch (NamingException e) {
            System.err.println("Passed: caught the expected Exception - " + e);

        } catch (Exception e) {
            System.err.println("Failed: caught an unexpected Exception - " + e);
            throw e;
        }
    }
}
