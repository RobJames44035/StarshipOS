/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import javax.naming.Binding;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8208542
 * @summary Tests that we get NameNotFoundException when doing a listBindings()
 *          on a nonexistent interior entry.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @run main ListBindingsInteriorNotFound
 */

public class ListBindingsInteriorNotFound extends ListTestBase {

    public ListBindingsInteriorNotFound() {
        setKey("host9.subdomain99");
    }

    public static void main(String[] args) throws Exception {
        new ListBindingsInteriorNotFound().run(args);
    }

    /*
     * Tests that we get NameNotFoundException when doing a listBindings()
     * on a nonexistent interior entry.
     */
    @Override
    public void runTest() throws Exception {
        setContext(new InitialDirContext(env()));
        NamingEnumeration<Binding> enumObj = context().listBindings(getKey());

        DNSTestUtils.debug("Enum is: " + enumObj);
        throw new RuntimeException("Failed: expecting NameNotFoundException");
    }

    @Override
    public boolean handleException(Exception e) {
        if (e instanceof NameNotFoundException) {
            System.out.println("Got expected exception: " + e);
            return true;
        }

        return super.handleException(e);
    }
}
