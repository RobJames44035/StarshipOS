/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package transform;

import java.net.URL;
import java.net.URLClassLoader;
import javax.xml.transform.TransformerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm transform.FactoryFindTest
 * @summary Test creating TransformerFactory with ContextClassLoader.
 */
public class FactoryFindTest {

    boolean myClassLoaderUsed = false;

    @Test
    public void testFactoryFind() throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Assert.assertTrue(factory.getClass().getClassLoader() == null);

        Thread.currentThread().setContextClassLoader(null);
        factory = TransformerFactory.newInstance();
        Assert.assertTrue(factory.getClass().getClassLoader() == null);

        Thread.currentThread().setContextClassLoader(new MyClassLoader());
        factory = TransformerFactory.newInstance();
        if (System.getSecurityManager() == null)
            Assert.assertTrue(myClassLoaderUsed);
        else
            Assert.assertFalse(myClassLoaderUsed);
    }

    class MyClassLoader extends URLClassLoader {

        public MyClassLoader() {
            super(new URL[0]);
        }

        public Class loadClass(String name) throws ClassNotFoundException {
            myClassLoaderUsed = true;
            return super.loadClass(name);
        }
    }
}
