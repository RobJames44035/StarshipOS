/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8208279
 * @summary Tests that when we remove environment properties from a context,
 *          its changes are reflected in the environment properties of any
 *          child context derived from the context.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @run main RemoveInherited
 */

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class RemoveInherited extends EnvTestBase {

    private static final String IRRELEVANT_PROPERTY_NAME = "some.irrelevant.property";
    private static final String IRRELEVANT_PROPERTY_VALUE = "somevalue";
    private static final String DNS_RECURSION_PROPERTY_NAME = "com.sun.jndi.dns.recursion";
    private static final String DNS_RECURSION_PROPERTY_VALUE = "false";

    public static void main(String[] args) throws Exception {
        new RemoveInherited().run(args);
    }

    /*
     * Tests that when we remove environment properties from a context,
     * its changes are reflected in the environment properties of any
     * child context derived from the context.
     */
    @Override public void runTest() throws Exception {
        initContext();

        // some.irrelevant.property been set in initContext(), should not be null
        Object val = context().removeFromEnvironment(IRRELEVANT_PROPERTY_NAME);

        if (!IRRELEVANT_PROPERTY_VALUE.equals(val)) {
            throw new RuntimeException(
                    "Failed: unexpected return value for removeFromEnvironment: "
                            + val);
        }

        Context child = (Context) context().lookup(getKey());

        Hashtable<?,?> envParent = context().getEnvironment();
        Hashtable<?,?> envChild = child.getEnvironment();

        DNSTestUtils.debug(child);
        DNSTestUtils.debug("Parent env: " + envParent);
        DNSTestUtils.debug("Child env: " + envChild);

        verifyEnvProperty(envParent, DNS_RECURSION_PROPERTY_NAME,
                DNS_RECURSION_PROPERTY_VALUE);
        verifyEnvProperty(envParent, IRRELEVANT_PROPERTY_NAME, null);
        verifyEnvProperty(envChild, DNS_RECURSION_PROPERTY_NAME,
                DNS_RECURSION_PROPERTY_VALUE);
        verifyEnvProperty(envChild, IRRELEVANT_PROPERTY_NAME, null);
    }

    private void initContext() throws NamingException {
        env().put(DNS_RECURSION_PROPERTY_NAME, DNS_RECURSION_PROPERTY_VALUE);
        env().put(IRRELEVANT_PROPERTY_NAME, IRRELEVANT_PROPERTY_VALUE);
        setContext(new InitialDirContext(env()));
    }
}
