/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8208483
 * @summary Tests that we can look up a DNS node
 *          and use an DirObjectFactory to return an object from it.
 *          Specify the all attributes to retrieve from the DNS node by using
 *          the com.sun.jndi.dns.lookup.attr property.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @build TestDnsObject DirAFactory
 * @run main/othervm LookupWithAnyAttrProp
 */

public class LookupWithAnyAttrProp extends LookupFactoryBase {

    public static void main(String[] args) throws Exception {
        new LookupWithAnyAttrProp().run(args);
    }

    /*
     * Tests that we can look up a DNS node
     * and use an DirObjectFactory to return an object from it.
     * Specify the all attributes to retrieve from the DNS node by using
     * the com.sun.jndi.dns.lookup.attr property.
     */
    @Override
    public void runTest() throws Exception {
        // initial context with object factory and lookup attr
        env().put(Context.OBJECT_FACTORIES, "DirAFactory");
        env().put(DNS_LOOKUP_ATTR, "*");
        setContext(new InitialDirContext(env()));

        Object obj = context().lookup(getKey());
        verifyLookupObjectAndValue(obj, ATTRIBUTE_VALUE);
    }
}
