/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8208279
 * @summary Tests that when we add the authoritative property to a context,
 *          that it affects subsequent context operations.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @run main AddOps
 */

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class AddOps extends EnvTestBase {

    public static void main(String[] args) throws Exception {
        new AddOps().run(args);
    }

    /*
     * Tests that when we add the authoritative property to a context,
     * that it affects subsequent context operations.
     */
    @Override public void runTest() throws Exception {
        setContext(new InitialDirContext(env()));
        addToEnvAndVerifyOldValIsNull(Context.AUTHORITATIVE, "true");
        retrieveAndVerifyAuthData();
        retrieveNonAuthData();
    }

    private void retrieveAndVerifyAuthData() throws NamingException {
        // Ensure that auth data retrieval is OK
        retrieveAndVerifyData(getFqdnUrl(), new String[] { "*" });
    }

    private void retrieveNonAuthData() throws NamingException {
        try {
            // Ensure that nonauth data retrieval cannot be retrieved
            Attributes retAttrs = context()
                    .getAttributes(getForeignFqdnUrl(), new String[] { "*" });
            DNSTestUtils.debug(context().getEnvironment());
            DNSTestUtils.debug(retAttrs);
            throw new RuntimeException(
                    "Failed: Expecting nonauth entry not found "
                            + getForeignFqdnUrl());

        } catch (NameNotFoundException e) {
            System.out.println("Got expected exception: " + e);
        }
    }
}
