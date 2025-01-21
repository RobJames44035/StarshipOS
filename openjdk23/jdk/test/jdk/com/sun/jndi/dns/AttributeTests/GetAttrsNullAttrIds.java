/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8198882
 * @summary Tests that we can get the attributes of a DNS entry.
 *          Use getAttributes form that accepts an attrIds parameter
 *          and supply null for it.
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetAttrsNullAttrIds
 */

import javax.naming.directory.Attributes;

public class GetAttrsNullAttrIds extends GetAttrsBase {

    public static void main(String[] args) throws Exception {
        new GetAttrsNullAttrIds().run(args);
    }

    @Override public void runTest() throws Exception {
        initContext();
        Attributes retAttrs = getAttributes();
        verifyAttributes(retAttrs);
    }

    /*
     * Tests that we can get the attributes of a DNS entry.
     * Use getAttributes form that accepts an attrIds parameter
     * and supply null for it.
     */
    @Override public Attributes getAttributes() throws Exception {
        return context().getAttributes(getKey(), null);
    }
}
