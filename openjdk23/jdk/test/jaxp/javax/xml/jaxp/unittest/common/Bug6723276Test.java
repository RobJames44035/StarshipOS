/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package common;

import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.parsers.SAXParserFactory;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6723276
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm common.Bug6723276Test
 * @summary Test JAXP class can be loaded by bootstrap classloader.
 */
public class Bug6723276Test {
    private static final String ERR_MSG = "org.apache.xerces.jaxp.SAXParserFactoryImpl not found";

    @Test
    public void testWithDefaultContextClassLoader() {
        try {
            SAXParserFactory.newInstance();
        } catch (Exception e) {
            if (e.getMessage().contains(ERR_MSG)) {
                Assert.fail(e.getMessage());
            }
        }
    }

    @Test
    public void testWithGivenURLContextClassLoader() {
        try {
            System.out.println(Thread.currentThread().getContextClassLoader());
            System.out.println(ClassLoader.getSystemClassLoader().getParent());
            Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[0], ClassLoader.getSystemClassLoader().getParent()));
            SAXParserFactory.newInstance();
        } catch (Exception e) {
            if (e.getMessage().contains(ERR_MSG)) {
                Assert.fail(e.getMessage());
            }
        }
    }

}
