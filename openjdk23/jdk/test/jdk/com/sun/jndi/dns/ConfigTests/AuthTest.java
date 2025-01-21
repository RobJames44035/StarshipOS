/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/*
 * @test
 * @bug 8200151
 * @summary Tests that we can get the attributes of DNS entries for
 *          authoritative data. And nonauthoritative data by default or
 *          java.naming.authoritative is set to false, but cannot when
 *          java.naming.authoritative is set to true.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @run main AuthTest -Dtestname=AuthDefault
 * @run main AuthTest -Dtestname=AuthFalse
 * @run main AuthTest -Dtestname=AuthTrue
 */

public class AuthTest extends AuthRecursiveBase {

    public static void main(String[] args) throws Exception {
        new AuthTest().run(args);
    }

    /*
     * Tests that we can get the attributes of DNS entries for
     * authoritative data. And nonauthoritative data by default or
     * java.naming.authoritative is set to false, but cannot when
     * java.naming.authoritative is set to true.
     */
    @Override
    public void runTest() throws Exception {
        String flag = parseFlagFromTestName();

        if (flag.equalsIgnoreCase("default")) {
            setContext(new InitialDirContext());
        } else {
            Hashtable<Object, Object> env = new Hashtable<>();
            DNSTestUtils.debug("set java.naming.authoritative to " + flag);
            // java.naming.authoritative is set to true or false
            env.put(Context.AUTHORITATIVE, flag);
            setContext(new InitialDirContext(env));
        }

        retrieveAndVerifyAuthData();
        retrieveNonAuthData(Boolean.parseBoolean(flag));
    }

    private void retrieveAndVerifyAuthData() throws NamingException {
        // Ensure that auth data retrieval is OK
        retrieveAndVerifyData(getFqdnUrl(), new String[] { "*" });
    }

    /*
     * If isAuth == true, ensure that nonauth data retrieval cannot be retrieved.
     * If isAuth == false, ensure that nonauth data retrieval is OK, skip
     * checking attributes for foreign; just successful operation is sufficient.
     */
    private void retrieveNonAuthData(boolean isAuth) throws NamingException {
        try {
            Attributes retAttrs = context()
                    .getAttributes(getForeignFqdnUrl(), new String[] { "*" });
            DNSTestUtils.debug(retAttrs);
            if (isAuth) {
                throw new RuntimeException(
                        "Failed: Expecting nonauth entry not found "
                                + getForeignFqdnUrl());
            }
        } catch (NameNotFoundException e) {
            if (isAuth) {
                System.out.println("Got expected exception: " + e);
            } else {
                throw e;
            }
        }
    }
}
