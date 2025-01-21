/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package stream.XMLInputFactoryTest;

import java.util.PropertyPermission;
import javax.xml.stream.XMLInputFactory;
import static jaxp.library.JAXPTestUtilities.setSystemProperty;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 6756677
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @compile MyInputFactory.java
 * @run testng/othervm stream.XMLInputFactoryTest.Bug6756677Test
 * @summary Test XMLInputFactory.newFactory(String factoryId, ClassLoader classLoader).
 */
public class Bug6756677Test {

    @Test
    public void testNewInstance() {
        String myFactory = "stream.XMLInputFactoryTest.MyInputFactory";
        try {
            setSystemProperty("MyInputFactory", myFactory);
            XMLInputFactory xif = XMLInputFactory.newInstance("MyInputFactory", null);
            System.out.println(xif.getClass().getName());
            Assert.assertTrue(xif.getClass().getName().equals(myFactory));

        } catch (UnsupportedOperationException oe) {
            Assert.fail(oe.getMessage());
        }
    }

    // newFactory was added in StAX 1.2
    @Test
    public void testNewFactory() {
        String myFactory = "stream.XMLInputFactoryTest.MyInputFactory";
        ClassLoader cl = null;
        try {
            setSystemProperty("MyInputFactory", myFactory);
            XMLInputFactory xif = XMLInputFactory.newFactory("MyInputFactory", cl);
            System.out.println(xif.getClass().getName());
            Assert.assertTrue(xif.getClass().getName().equals(myFactory));

        } catch (UnsupportedOperationException oe) {
            Assert.fail(oe.getMessage());
        }
    }


    String XMLInputFactoryClassName = "com.sun.xml.internal.stream.XMLInputFactoryImpl";
    String XMLInputFactoryID = "javax.xml.stream.XMLInputFactory";
    ClassLoader CL = null;

    /*
     * test for XMLInputFactory.newInstance(java.lang.String factoryClassName,
     * java.lang.ClassLoader classLoader) classloader is null and
     * factoryClassName points to correct implementation of
     * javax.xml.stream.XMLInputFactory , should return newInstance of
     * XMLInputFactory
     */
    @Test
    public void test29() throws Exception {
        setSystemProperty(XMLInputFactoryID, XMLInputFactoryClassName);
        XMLInputFactory xif = XMLInputFactory.newInstance(XMLInputFactoryID, CL);
        Assert.assertTrue(xif instanceof XMLInputFactory, "xif should be an instance of XMLInputFactory");
    }

    /*
     * test for XMLInputFactory.newInstance(java.lang.String factoryClassName,
     * java.lang.ClassLoader classLoader) classloader is
     * default(Class.getClassLoader()) and factoryClassName points to correct
     * implementation of javax.xml.stream.XMLInputFactory , should return
     * newInstance of XMLInputFactory
     */
    @Test
    public void test31() throws Exception {
        Bug6756677Test test3 = new Bug6756677Test();
        ClassLoader cl = (test3.getClass()).getClassLoader();
        setSystemProperty(XMLInputFactoryID, XMLInputFactoryClassName);
        XMLInputFactory xif = XMLInputFactory.newInstance(XMLInputFactoryID, cl);
        Assert.assertTrue(xif instanceof XMLInputFactory, "xif should be an instance of XMLInputFactory");
    }
}
