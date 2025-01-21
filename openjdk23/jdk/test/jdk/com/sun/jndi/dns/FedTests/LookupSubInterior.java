/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8210339
 * @summary Look up of a Interior using a composite name with DNS name as
 *          first component
 * @library ../lib/ ../FactoryTests/
 * @modules java.naming/com.sun.jndi.toolkit.dir
 *          java.base/sun.security.util
 * @build FedSubordinateNs FedObjectFactory
 * @run main/othervm LookupSubInterior
 */

public class LookupSubInterior extends LookupFactoryBase {

    // pre defined attribute value for '/a/b'
    public static final String ATTRIBUTE_VALUE = "b";

    public static void main(String[] args) throws Exception {
        new LookupSubInterior().run(args);
    }

    /*
     * Look up of a Interior using a composite name with DNS name as
     * first component
     */
    @Override
    public void runTest() throws Exception {
        // initial context with object factory
        env().put(Context.OBJECT_FACTORIES, "FedObjectFactory");
        setContext(new InitialDirContext(env()));

        Object obj = context().lookup(getKey() + "/a/b");

        verifyLookupObject(obj, DirContext.class);

        Attributes attrs = ((DirContext) obj).getAttributes("");
        Attribute attr = attrs.get("name");

        DNSTestUtils.debug(getKey() + "/a/b is: " + attrs);
        verifyAttribute(attr);
    }

    private void verifyAttribute(Attribute attr) throws NamingException {
        if (attr == null || !ATTRIBUTE_VALUE.equals(attr.get())) {
            throw new RuntimeException(
                    "Expecting attribute value: " + ATTRIBUTE_VALUE
                            + ", but actual: " + (attr != null ?
                            attr.get() :
                            attr));
        }
    }
}
