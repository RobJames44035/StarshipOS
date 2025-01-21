/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 *
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.rmi.server.RMIClassLoader;
import java.rmi.server.RMIClassLoaderSpi;

/**
 * TestProvider2 extends TestProvider with very similar behavior, but
 * with its own static fields, to allow a test to distinguish between
 * an installed TestProvider2 being used, or a property-specified
 * TestProvider being used.
 */
public class TestProvider2 extends TestProvider {

    public static final Class loadClassReturn =
        (new Object() { }).getClass();
    public static final Class loadProxyClassReturn =
        (new Object() { }).getClass();
    public static final ClassLoader getClassLoaderReturn =
        URLClassLoader.newInstance(new URL[0]);
    public static final String getClassAnnotationReturn = new String();

    public static List invocations =
        Collections.synchronizedList(new ArrayList(1));

    public TestProvider2() {
        System.err.println("TestProvider2()");
    }

    public Class loadClass(String codebase, String name,
                           ClassLoader defaultLoader)
        throws MalformedURLException, ClassNotFoundException
    {
        invocations.add(new Invocation(loadClassMethod,
            new Object[] { codebase, name, defaultLoader }));

        return loadClassReturn;
    }

    public Class loadProxyClass(String codebase, String[] interfaces,
                                ClassLoader defaultLoader)
        throws MalformedURLException, ClassNotFoundException
    {
        invocations.add(new Invocation(loadProxyClassMethod,
            new Object[] { codebase, interfaces, defaultLoader }));

        return loadProxyClassReturn;
    }

    public ClassLoader getClassLoader(String codebase)
        throws MalformedURLException
    {
        invocations.add(new Invocation(
            getClassLoaderMethod, new Object[] { codebase }));

        return getClassLoaderReturn;
    }

    public String getClassAnnotation(Class<?> cl) {
        invocations.add(new Invocation(
            getClassAnnotationMethod, new Object[] { cl }));

        return getClassAnnotationReturn;
    }
}
