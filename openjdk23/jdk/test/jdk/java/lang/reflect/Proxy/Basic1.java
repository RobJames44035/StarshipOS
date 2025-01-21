/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4227192 4487672
 * @summary This is a basic functional test of the dynamic proxy API (part 1).
 * @author Peter Jones
 *
 * @build Basic1
 * @run main Basic1
 */

import java.lang.reflect.*;
import java.security.*;
import java.util.*;

public class Basic1 {

    public static void main(String[] args) {

        System.err.println(
            "\nBasic functional test of dynamic proxy API, part 1\n");

        try {
            Class<?>[] interfaces =
                new Class<?>[] { Runnable.class, Observer.class };

            ClassLoader loader = ClassLoader.getSystemClassLoader();

            /*
             * Generate a proxy class.
             */
            Class<?> proxyClass = Proxy.getProxyClass(loader, interfaces);
            System.err.println("+ generated proxy class: " + proxyClass);

            /*
             * Verify that it is public, final, and not abstract.
             */
            int flags = proxyClass.getModifiers();
            System.err.println(
                "+ proxy class's modifiers: " + Modifier.toString(flags));
            if (!Modifier.isPublic(flags)) {
                throw new RuntimeException("proxy class in not public");
            }
            if (!Modifier.isFinal(flags)) {
                throw new RuntimeException("proxy class in not final");
            }
            if (Modifier.isAbstract(flags)) {
                throw new RuntimeException("proxy class in abstract");
            }

            /*
             * Verify that it is assignable to the proxy interfaces.
             */
            for (Class<?> intf : interfaces) {
                if (!intf.isAssignableFrom(proxyClass)) {
                    throw new RuntimeException(
                        "proxy class not assignable to proxy interface " +
                        intf.getName());
                }
            }

            /*
             * Verify that it has the given permutation of interfaces.
             */
            List<Class<?>> l1 = Arrays.asList(interfaces);
            List<Class<?>> l2 = Arrays.asList(proxyClass.getInterfaces());
            System.err.println("+ proxy class's interfaces: " + l2);
            if (!l1.equals(l2)) {
                throw new RuntimeException(
                    "proxy class interfaces are " + l2 +
                    " (expected " + l1 + ")");
            }

            /*
             * Verify that system agress that it is a proxy class.
             */
            if (Proxy.isProxyClass(Object.class)) {
                throw new RuntimeException(
                    "Proxy.isProxyClass returned true for java.lang.Object");
            }
            if (!Proxy.isProxyClass(proxyClass)) {
                throw new RuntimeException(
                    "Proxy.isProxyClass returned false for proxy class");
            }

            /*
             * Verify that its protection domain is the bootstrap domain.
             */
            ProtectionDomain pd = proxyClass.getProtectionDomain();
            System.err.println("+ proxy class's protection domain: " + pd);
            if (!pd.implies(new AllPermission())) {
                throw new RuntimeException(
                    "proxy class does not have AllPermission");
            }

            /*
             * Verify that it has a constructor that takes an
             * InvocationHandler instance.
             */
            Constructor<?> cons = proxyClass.getConstructor(InvocationHandler.class);

            /*
             * Test constructor with null InvocationHandler
             */
            try {
                cons.newInstance(new Object[] { null });
                throw new RuntimeException("Expected NullPointerException thrown");
            } catch (InvocationTargetException e) {
                Throwable t = e.getTargetException();
                if (!(t instanceof NullPointerException)) {
                    throw t;
                }
            }

            /*
             * Construct a proxy instance.
             */
            Handler handler = new Handler();
            Object proxy = cons.newInstance(handler);
            handler.currentProxy = proxy;

            /*
             * Invoke a method on a proxy instance.
             */
            Method m = Runnable.class.getMethod("run");
            ((Runnable) proxy).run();
            if (!handler.lastMethod.equals(m)) {
                throw new RuntimeException(
                    "proxy method invocation failure (lastMethod = " +
                        handler.lastMethod + ")");
            }

            System.err.println("\nTEST PASSED");

        } catch (Throwable e) {
            System.err.println("\nTEST FAILED:");
            e.printStackTrace();
            throw new RuntimeException("TEST FAILED: " + e.toString());
        }
    }

    public static class Handler implements InvocationHandler {

        Object currentProxy;
        Method lastMethod;

        public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
        {
            if (proxy != currentProxy) {
                throw new RuntimeException(
                    "wrong proxy instance passed to invoke method");
            }
            lastMethod = method;
            return null;
        }
    }
}
