/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4910892
 * @summary 4518403 was not properly fixed.   hashcode should be hashCode.
 * @library /javax/net/ssl/templates
 * @run main/othervm HashCodeMissing
 *
 *     SunJSSE does not support dynamic system properties, no way to re-use
 *     system properties in samevm/agentvm mode.
 * @author Brad Wetmore
 */

import java.io.*;
import javax.net.ssl.*;
import java.lang.reflect.*;

public class HashCodeMissing extends SSLSocketTemplate {

    /*
     * Turn on SSL debugging?
     */
    static boolean debug = false;


    @Override
    protected void runServerApplication(SSLSocket sslSocket) throws Exception {
        InputStream sslIS = sslSocket.getInputStream();
        OutputStream sslOS = sslSocket.getOutputStream();

        sslIS.read();
        sslOS.write(85);
        sslOS.flush();

    }

    @Override
    protected void runClientApplication(SSLSocket sslSocket) throws Exception {

        InputStream sslIS = sslSocket.getInputStream();
        OutputStream sslOS = sslSocket.getOutputStream();

        sslOS.write(280);
        sslOS.flush();
        sslIS.read();

        SSLSession sslSession = sslSocket.getSession();

        sslSocket.close();

        Class clazz = sslSession.getClass();

        /*
         * Real test is done here
         */
        System.out.println("Getting 'hashCode'");
        Method method = clazz.getDeclaredMethod("hashCode", new Class [0]);
        System.out.println("Method = " + method);
    }

    public static void main(String[] args) throws Exception {

        if (debug)
            System.setProperty("javax.net.debug", "all");

        /*
         * Start the tests.
         */
        new HashCodeMissing().run();
    }

}
