/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4871761
 * @summary Tests that JMXServiceErrorException is correctly emitted.
 * @author Daniel Fuchs
 * @modules java.management.rmi
 *          java.management/com.sun.jmx.remote.security
 * @run clean JMXServerErrorTest
 * @run build JMXServerErrorTest
 * @run main  JMXServerErrorTest
 */

import java.util.HashMap ;
import java.util.Map ;
import java.net.MalformedURLException;
import java.io.IOException ;

import javax.management.MBeanServerFactory;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.MBeanServerInvocationHandler;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXServerErrorException;

import com.sun.jmx.remote.security.MBeanServerAccessController;

public class JMXServerErrorTest {

    public static String urls[] = {
        "service:jmx:rmi://", "service:jmx:iiop://","service:jmx:jmxmp://"
    };

    public static class KaefferError extends Error {
        public KaefferError(String message, Throwable cause) {
            super(message,cause);
        }
    }

    /**
     * generates an error...
     **/
    public static class MBeanServerKaeffer
        extends MBeanServerAccessController {

        MBeanServerKaeffer(MBeanServer srv) {
            super();
            setMBeanServer(srv);
        }


        /**
         * Check if the caller can do read operations. This method does
         * nothing if so, otherwise throws SecurityException.
         */
        protected void checkRead() {
            // do nothing
        }

        /**
         * Check if the caller can do write operations.  This method does
         * nothing if so, otherwise throws SecurityException.
         */
        protected void checkWrite() {
            // generate error
            throw new KaefferError("Try to catch this!",null);
        }
    }

    public interface KaefferMBean {
        public String getThis() throws IOException;
        public void   setThis(String that) throws IOException;
        public String doThis(String that) throws IOException;
    }

    public static class Kaeffer implements KaefferMBean {
        String that = "";
        public Kaeffer(String that) {
            setThis(that);
        }
        public String getThis() {return that;}
        public void   setThis(String that) { this.that=(that==null)?"":that; }
        public String doThis(String that)  { return this.that += " " + that;}
    }

    public void test(String url) throws Exception {
        final JMXServiceURL jurl     = new JMXServiceURL(url);
        final ObjectName    kname    = new ObjectName(":type=Kaeffer");
        final MBeanServer   mbs      = MBeanServerFactory.newMBeanServer();
        final String        that     = "that";
        mbs.registerMBean(new Kaeffer(that),kname);
        final MBeanServer   kbs      = new MBeanServerKaeffer(mbs);

        final JMXConnectorServer cs;
        try {
            cs=JMXConnectorServerFactory.newJMXConnectorServer(jurl,null,kbs);
        } catch (MalformedURLException m) {
            if ("jmxmp".equals(jurl.getProtocol()) || "iiop".equals(jurl.getProtocol())) {
                // OK, we may not have this in the classpath...
                System.out.println("WARNING: Skipping protocol: " + jurl);
                return;
            }
            throw m;
        }

        final ObjectName    cname    =
            new ObjectName(":type=JMXConnectorServer");
        mbs.registerMBean(cs,cname);
        cs.start();
        JMXConnector c = null;
        try {
            c = JMXConnectorFactory.connect(cs.getAddress(),null);

            final MBeanServerConnection mbsc = c.getMBeanServerConnection();
            final KaefferMBean kaeffer = (KaefferMBean)
                MBeanServerInvocationHandler.
                newProxyInstance(mbsc, kname, KaefferMBean.class, false);
            final String that1 = kaeffer.getThis();
            if (!that.equals(that1))
                throw new Exception("Unexpected string returned by " +
                                    kname + ": " + that1);
            try {
                kaeffer.setThis("but not that");
                throw new Exception("Expected JMXServerErrorException"+
                                    " not thrown"+
                                    " for setAttribute \"This\" ");
            } catch (JMXServerErrorException jsee) {
                if (!(jsee.getCause() instanceof KaefferError)) {
                    final Exception e =
                        new Exception("Expected JMXServerErrorException"+
                                      " is not an instance of " +
                                      KaefferError.class.getName()+
                                      ": " + jsee.getCause());
                    e.initCause(jsee);
                    throw e;
                }
                System.out.println("Got expected error: " +  jsee);
            }

            try {
                kaeffer.doThis("but not that");
                throw new Exception("Expected JMXServerErrorException" +
                                    " not thrown"+
                                    " for invoke \"doThis\" ");
            } catch (JMXServerErrorException jsee) {
                if (!(jsee.getCause() instanceof KaefferError)) {
                    final Exception e =
                        new Exception("Expected JMXServerErrorException"+
                                      " is not an instance of " +
                                      KaefferError.class.getName()+
                                      ": " + jsee.getCause());
                    e.initCause(jsee);
                    throw e;
                }
                System.out.println("Got expected error: " +  jsee);
            }
        } finally {
            if (c != null) try { c.close(); }
            catch (Exception x) {
                System.err.println("Failed to close client: " + x);
                throw x;
            }
            try { cs.stop(); }
            catch (Exception x) {
                System.err.println("Failed to stop server: " + x);
                throw x;
            }
        }
    }

    public static void main(String args[]) {
        final JMXServerErrorTest test = new JMXServerErrorTest();
        int errCount=0;
        for (int i=0; i<urls.length; i++) {
            try {
                System.out.println("Trying with url: " + urls[i]);
                test.test(urls[i]);
                System.out.println("PASSED: test passed for: " + urls[i]);
            } catch (Exception x) {
                errCount++;
                System.err.println("FAILED: test failed for " + urls[i] +
                                   ": " + x);
                x.printStackTrace();
            }
        }
        if (errCount != 0) System.exit(errCount);
    }
}
