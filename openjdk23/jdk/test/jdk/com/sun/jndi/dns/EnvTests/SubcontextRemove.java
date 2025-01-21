/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8208279
 * @summary Tests that when we remove environment properties from a child
 *          context, its changes do not affect the environment properties of
 *          the parent context.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @run main SubcontextRemove
 */

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class SubcontextRemove extends EnvTestBase {

    public static void main(String[] args) throws Exception {
        new SubcontextRemove().run(args);
    }

    /*
     * Tests that when we remove environment properties from a child
     * context, its changes do not affect the environment properties of
     * the parent context.
     */
    @Override public void runTest() throws Exception {
        initContext();

        Context child = (Context) context().lookup(getKey());

        // some.irrelevant.property been set in initContext(), should not be null
        Object val = child.removeFromEnvironment("some.irrelevant.property");

        if (!"somevalue".equals(val)) {
            throw new RuntimeException(
                    "Failed: unexpected return value for removeFromEnvironment: "
                            + val);
        }

        Hashtable<?,?> envParent = context().getEnvironment();
        Hashtable<?,?> envChild = child.getEnvironment();

        DNSTestUtils.debug(child);
        DNSTestUtils.debug("Parent env: " + envParent);
        DNSTestUtils.debug("Child env: " + envChild);

        verifyEnvProperty(envParent, "com.sun.jndi.dns.recursion", "false");
        verifyEnvProperty(envParent, "some.irrelevant.property", "somevalue");
        verifyEnvProperty(envChild, "com.sun.jndi.dns.recursion", "false");
        verifyEnvProperty(envChild, "some.irrelevant.property", null);
    }

    private void initContext() throws NamingException {
        env().put("com.sun.jndi.dns.recursion", "false");
        env().put("some.irrelevant.property", "somevalue");
        setContext(new InitialDirContext(env()));
    }
}
