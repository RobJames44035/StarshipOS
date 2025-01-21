/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package common.catalog;

/**
 * @test @bug 8306055
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @modules java.xml/jdk.xml.internal
 * @run driver common.catalog.SAXTest 0 // verifies default setting catalog.resolve=allow
 * @run driver common.catalog.SAXTest 1 // verifies overriding with catalog.resolve=strict in a config file
 * @run driver common.catalog.SAXTest 2 // verifies overriding with system property
 * @run driver common.catalog.SAXTest 3 // verifies overriding with factory setting (catalog.resolve=strict)
 * @run driver common.catalog.SAXTest 4 // verifies external DTD resolution with the JDK Catalog while resolve=strict in config file
 * @run driver common.catalog.SAXTest 5 // verifies external DTD resolution with the JDK Catalog while resolve=strict in API setting
 * @run driver common.catalog.SAXTest 6 // verifies external DTD resolution with a custom Catalog while resolve=strict in config file
 * @run driver common.catalog.SAXTest 7 // verifies external DTD resolution with a custom Catalog while resolve=strict in API setting
 * @run driver common.catalog.SAXTest 8 // verifies external parameter are resolved with a custom Catalog though resolve=strict in API setting
 * @run driver common.catalog.SAXTest 9 // verifies XInclude are resolved with a custom Catalog though resolve=strict in API setting
 * @summary verifies DOM's support of the JDK Catalog.

 */
public class SAXTest extends CatalogTestBase {
    public static void main(String args[]) throws Exception {
        new SAXTest().run(args[0]);
    }

    public void run(String index) throws Exception {
        paramMap(Processor.SAX, null, index);
        super.testSAX(filename, fsp, state, config, sysProp, apiProp, cc, expectError, error);
    }
}
