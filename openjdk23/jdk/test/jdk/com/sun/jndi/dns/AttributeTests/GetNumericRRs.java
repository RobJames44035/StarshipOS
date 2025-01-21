/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8198882
 * @summary Tests that we can get the attributes of a DNS entry by naming
 *          specific RRs by their type codes. Omit NAPTR for now because
 *          bind doesn't support it.
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetNumericRRs
 */

import javax.naming.directory.Attributes;

public class GetNumericRRs extends GetRRsBase {

    public GetNumericRRs() {
        // set new test data instead of default value
        setNumAttrs(new String[][] { { "1", "15", "13", "16" }, { "2", "6" },
                { "28" }, { "5" }, { "33" }, { "12" }, });
    }

    public static void main(String[] args) throws Exception {
        new GetNumericRRs().run(args);
    }

    /*
     * Tests that we can get the attributes of a DNS entry by naming
     * specific RRs by their type codes. Omit NAPTR for now because
     * bind doesn't support it.
     */
    @Override public void runTest() throws Exception {
        initContext();

        for (int i = 0; i < ROOT_LIMIT; i++) {
            Attributes retAttrs = getAttributes(getKeys()[i], getNumAttrs()[i]);
            verifyAttributes(retAttrs, getAttrs()[i]);
        }

        switchToRootUrl();

        for (int i = ROOT_LIMIT; i < getKeys().length; i++) {
            Attributes retAttrs = getAttributes(getKeys()[i], getNumAttrs()[i]);
            verifyAttributes(retAttrs, getAttrs()[i]);
        }
    }
}
