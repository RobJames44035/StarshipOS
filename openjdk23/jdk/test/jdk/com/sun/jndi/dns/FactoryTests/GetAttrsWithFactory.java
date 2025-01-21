/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8208483
 * @summary Tests that setting the object factory and
 *          com.sun.jndi.dns.lookup.attr properties does not affect how
 *          we can get the attributes of a DNS entry.
 *          Use getAttributes form that has no attrIds parameter.
 * @library ../lib/ ../AttributeTests/
 * @modules java.base/sun.security.util
 * @build TestDnsObject TxtFactory
 * @run main/othervm GetAttrsWithFactory
 */

public class GetAttrsWithFactory extends GetAttrsBase {

    public static void main(String[] args) throws Exception {
        new GetAttrsWithFactory().run(args);
    }

    /*
     * Tests that setting the object factory and
     * com.sun.jndi.dns.lookup.attr properties does not affect how
     * we can get the attributes of a DNS entry.
     */
    @Override
    public void runTest() throws Exception {
        // initial context with object factory and lookup attr
        env().put(Context.OBJECT_FACTORIES, "TxtFactory");
        env().put(LookupFactoryBase.DNS_LOOKUP_ATTR, "A");
        setContext(new InitialDirContext(env()));

        Attributes retAttrs = getAttributes();
        verifyAttributes(retAttrs);
    }

    /*
     * Use getAttributes form that has no attrIds parameter.
     */
    @Override
    public Attributes getAttributes() throws NamingException {
        return context().getAttributes(getKey());
    }
}
