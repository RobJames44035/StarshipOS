/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import javax.naming.CannotProceedException;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8210339
 * @summary Try to do a look up beyond DNS with no appropriate subordinate ns
 *          should result in a CannotProceedException.
 * @library ../lib/
 * @modules java.base/sun.security.util
 * @run main CannotProceed
 */

public class CannotProceed extends DNSTestBase {

    public static void main(String[] args) throws Exception {
        new CannotProceed().run(args);
    }

    /*
     * Try to do a look up beyond DNS with no appropriate subordinate ns
     * should result in a CannotProceedException.
     */
    @Override
    public void runTest() throws Exception {
        setContext(new InitialDirContext(env()));
        Object nns = context().lookup("host1" + "/a/b/c");
        DNSTestUtils.debug("obj is: " + nns);
        throw new RuntimeException("Failed: expecting CannotProceedException");
    }

    @Override
    public boolean handleException(Exception e) {
        if (e instanceof CannotProceedException) {
            System.out.println("Got expected exception: " + e);
            return true;
        }

        return super.handleException(e);
    }
}
