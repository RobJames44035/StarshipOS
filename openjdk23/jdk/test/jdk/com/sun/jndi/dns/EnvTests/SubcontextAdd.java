/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8208279
 * @summary Tests that when we add environment properties to a child context,
 *          its changes do not affect the environment properties of the parent
 *          context.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @run main SubcontextAdd
 */

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class SubcontextAdd extends EnvTestBase {

    public static void main(String[] args) throws Exception {
        new SubcontextAdd().run(args);
    }

    /*
     * Tests that when we add environment properties to a child context,
     * its changes do not affect the environment properties of the parent
     * context.
     */
    @Override public void runTest() throws Exception {
        setContext(new InitialDirContext(env()));

        Context child = (Context) context().lookup(getKey());

        addToEnvAndVerifyOldValIsNull(child, "com.sun.jndi.dns.recursion",
                "false");
        addToEnvAndVerifyOldValIsNull(child, "some.irrelevant.property",
                "somevalue");

        Hashtable<?,?> envParent = context().getEnvironment();
        Hashtable<?,?> envChild = child.getEnvironment();

        DNSTestUtils.debug(child);
        DNSTestUtils.debug("Parent env: " + envParent);
        DNSTestUtils.debug("Child env: " + envChild);

        verifyEnvProperty(envParent, "com.sun.jndi.dns.recursion", null);
        verifyEnvProperty(envParent, "some.irrelevant.property", null);
        verifyEnvProperty(envChild, "com.sun.jndi.dns.recursion", "false");
        verifyEnvProperty(envChild, "some.irrelevant.property", "somevalue");
    }
}
