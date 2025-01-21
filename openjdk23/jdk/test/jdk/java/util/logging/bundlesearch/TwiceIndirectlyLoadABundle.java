/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

/**
 * This class constructs a scenario where a bundle is accessible on the call
 * stack two levels up from the call to getLogger(), but not on the immediate
 * caller.  This tests that getLogger() isn't doing a stack crawl more than one
 * level up to find a bundle.
 *
 * @author Jim Gish
 */
public class TwiceIndirectlyLoadABundle {

    private static final String rbName = "StackSearchableResource";

    public boolean loadAndTest() throws Throwable {
        // Find out where we are running from so we can setup the URLClassLoader URLs
        // test.src and test.classes will be set if running in jtreg, but probably
        // not otherwise
        String testDir = System.getProperty("test.src", System.getProperty("user.dir"));
        String testClassesDir = System.getProperty("test.classes",
                                                   System.getProperty("user.dir"));
        URL[] urls = new URL[2];

        // Allow for both jtreg and standalone cases here
        // Unlike the 1-level test where we can get the bundle from the caller's
        // class loader, for this one we don't want to expose the resource directory
        // to the next class.  That way we're invoking the LoadItUp2Invoker class
        // from this class that does have access to the resources (two levels
        // up the call stack), but the Invoker itself won't have access to resource
        urls[0] = Paths.get(testDir,"resources").toUri().toURL();
        urls[1] = Paths.get(testClassesDir).toUri().toURL();

        // Make sure we can find it via the URLClassLoader
        URLClassLoader yetAnotherResourceCL = new URLClassLoader(urls, null);
        Class<?> loadItUp2InvokerClazz = Class.forName("LoadItUp2Invoker", true,
                                                       yetAnotherResourceCL);
        ClassLoader actual = loadItUp2InvokerClazz.getClassLoader();
        if (actual != yetAnotherResourceCL) {
            throw new Exception("LoadItUp2Invoker was loaded by an unexpected CL: "
                                 + actual);
        }
        Object loadItUp2Invoker = loadItUp2InvokerClazz.newInstance();

        Method setupMethod = loadItUp2InvokerClazz.getMethod("setup",
                urls.getClass(), String.class);
        try {
            // For the next class loader we create, we want to leave off
            // the resources.  That way loadItUp2Invoker will have access to
            // them, but the next class won't.
            URL[] noResourceUrl = new URL[1];
            noResourceUrl[0] = urls[1];  // from above -- just the test classes
            setupMethod.invoke(loadItUp2Invoker, noResourceUrl, rbName);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }

        Method testMethod = loadItUp2InvokerClazz.getMethod("test");
        try {
            return (Boolean) testMethod.invoke(loadItUp2Invoker);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
}
