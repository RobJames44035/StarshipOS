/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8210339
 * @summary Tests that we can get the attributes of the interior node
 *          in the subordinate naming system of a DNS entry.
 *          Use getAttributes form that has no attrIds parameter.
 * @library ../lib/ ../AttributeTests/
 * @modules java.naming/com.sun.jndi.toolkit.dir
 *          java.base/sun.security.util
 * @build FedSubordinateNs FedObjectFactory
 * @run main/othervm GetAttrsSubInterior
 */

public class GetAttrsSubInterior extends GetAttrsBase {

    // pre defined attribute value for '/a/b'
    public static final String ATTRIBUTE_VALUE = "b";

    public GetAttrsSubInterior() {
        setMandatoryAttrs("name", "description");
    }

    public static void main(String[] args) throws Exception {
        new GetAttrsSubInterior().run(args);
    }

    /*
     * Tests that we can get the attributes of the interior node
     * in the subordinate naming system of a DNS entry.
     */
    @Override
    public void runTest() throws Exception {
        env().put(Context.OBJECT_FACTORIES, "FedObjectFactory");
        setContext(new InitialDirContext(env()));

        Attributes retAttrs = getAttributes();
        Attribute attr = retAttrs.get("name");
        verifyAttributes(retAttrs);
        verifyAttribute(attr);
    }

    /*
     * Use getAttributes form that has no attrIds parameter.
     */
    @Override
    public Attributes getAttributes() throws NamingException {
        return context().getAttributes(getKey() + "/a/b");
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
