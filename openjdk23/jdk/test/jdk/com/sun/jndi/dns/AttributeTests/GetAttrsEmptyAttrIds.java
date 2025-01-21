/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8198882
 * @summary Tests that we can get the attributes of a DNS entry.
 *          Specify that no attributes are to be returned.
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetAttrsEmptyAttrIds
 */

import javax.naming.directory.Attributes;

public class GetAttrsEmptyAttrIds extends GetAttrsBase {

    public GetAttrsEmptyAttrIds() {
        // set new test data instead of default value
        setMandatoryAttrs(new String[] {});
    }

    public static void main(String[] args) throws Exception {
        new GetAttrsEmptyAttrIds().run(args);
    }

    @Override public void runTest() throws Exception {
        initContext();
        Attributes retAttrs = getAttributes();
        verifyAttributes(retAttrs);
    }

    /*
     * Tests that we can get the attributes of a DNS entry.
     * Specify that no attributes are to be returned.
     */
    @Override public Attributes getAttributes() throws Exception {
        return context().getAttributes(getKey(), new String[] {});
    }
}
