/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5092515
 * @summary Test how to unwrap a user specific exception
 * @author Shanliang JIANG
 *
 * @run clean MBeanServerInvocationHandlerExceptionTest
 * @run build MBeanServerInvocationHandlerExceptionTest
 * @run main MBeanServerInvocationHandlerExceptionTest
 */


import java.io.IOException;
import javax.management.*;

public class MBeanServerInvocationHandlerExceptionTest {
    public static void main(String[] args) throws Exception {
        System.out.println(">>> Test how for the MBeanServerInvocationHandler to "+
                           "unwrap a user specific exception.");

        final MBeanServer mbs = MBeanServerFactory.newMBeanServer();
        final ObjectName name = new ObjectName("a:b=c");
        mbs.registerMBean(new Test(), name);
        TestMBean proxy = (TestMBean)
            MBeanServerInvocationHandler.newProxyInstance(mbs,
                                                          name,
                                                          TestMBean.class,
                                                          false);

        // test the method "getter"
        System.out.println(">>> Test the method getter to get an IOException.");
        try {
            proxy.getIOException();
        } catch (IOException e) {
            System.out.println(">>> Test passed: got expected exception:");
            // e.printStackTrace(System.out);
        } catch (Throwable t) {
            System.out.println(">>> Test failed: got wrong exception:");
            t.printStackTrace(System.out);

            throw new RuntimeException("Did not get an expected IOException.");
        }

        // test the method "setter"
        System.out.println(">>> Test the method setter to get a RuntimeException.");
        try {
            proxy.setRuntimeException("coucou");
        } catch (UnsupportedOperationException ue) {
            System.out.println(">>> Test passed: got expected exception:");
            //ue.printStackTrace(System.out);
        } catch (Throwable t) {
            System.out.println(">>> Test failed: got wrong exception:");
            t.printStackTrace(System.out);

            throw new RuntimeException("Did not get an expected Runtimeexception.");
        }

        // test the method "invoke"
        System.out.println(">>> Test the method invoke to get an Error.");
        try {
            proxy.invokeError();
        } catch (AssertionError ae) {
            System.out.println(">>> Test passed: got expected exception:");
            //ue.printStackTrace(System.out);
        } catch (Throwable t) {
            System.out.println(">>> Test failed: got wrong exception:");
            t.printStackTrace(System.out);

            throw new RuntimeException("Did not get an expected Error.");
        }
    }

    public static interface TestMBean {
        public Object getIOException() throws IOException;

        public void setRuntimeException(String s) throws RuntimeException;

        public void invokeError() throws Error;

    }

    public static class Test implements TestMBean {
        public Object getIOException() throws IOException {
            throw new IOException("oops");
        }

        public void setRuntimeException(String s) throws RuntimeException {
            throw new UnsupportedOperationException(s);
        }

        public void invokeError() throws Error {
            throw new AssertionError();
        }
    }
}
