/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8198882
 * @summary Tests that we can get the attributes of a DNS entry by naming
 *          specific RRs by name. Omit NAPTR for now because bind doesn't
 *          support it.
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetRRs
 */

import javax.naming.directory.Attributes;

public class GetRRs extends GetRRsBase {

    public static void main(String[] args) throws Exception {
        new GetRRs().run(args);
    }

    /*
     * Tests that we can get the attributes of a DNS entry by naming
     * specific RRs by name. Omit NAPTR for now because bind doesn't
     * support it.
     */
    @Override public void runTest() throws Exception {
        initContext();

        for (int i = 0; i < ROOT_LIMIT; i++) {
            Attributes retAttrs = getAttributes(getKeys()[i], getAttrs()[i]);
            verifyAttributes(retAttrs, getAttrs()[i]);
        }

        switchToRootUrl();

        for (int i = ROOT_LIMIT; i < getKeys().length; i++) {
            Attributes retAttrs = getAttributes(getKeys()[i], getAttrs()[i]);
            verifyAttributes(retAttrs, getAttrs()[i]);
        }
    }
}
