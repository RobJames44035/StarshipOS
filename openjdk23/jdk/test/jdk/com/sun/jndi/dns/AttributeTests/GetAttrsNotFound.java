/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8198882
 * @summary Tests that get attributes of a missing child entry results
 *          in NameNotFoundException.
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetAttrsNotFound
 */

import javax.naming.NameNotFoundException;
import javax.naming.directory.Attributes;

public class GetAttrsNotFound extends GetAttrsBase {

    public GetAttrsNotFound() {
        // set new test data instead of default value
        setKey("notfound");
    }

    public static void main(String[] args) throws Exception {
        new GetAttrsNotFound().run(args);
    }

    @Override public void runTest() throws Exception {
        initContext();
        Attributes retAttrs = getAttributes();
        DNSTestUtils.debug(retAttrs);
        throw new RuntimeException(
                "Failed: getAttributes succeeded unexpectedly");
    }

    /*
     * Tests that get attributes of a missing child entry results
     * in NameNotFoundException.
     */
    @Override public Attributes getAttributes() throws Exception {
        return context().getAttributes(getKey());
    }

    @Override public boolean handleException(Exception e) {
        if (e instanceof NameNotFoundException) {
            System.out.println("Got exception as expected : " + e);
            return true;
        }

        return super.handleException(e);
    }
}
