/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8198882
 * @summary Tests that we can get the attributes of a DNS entry.
 *          Supply at least one nonexistent attribute name in attrIds
 *          (should be ignored).
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetAttrsNonExistentAttrIds
 */

import javax.naming.directory.Attributes;

public class GetAttrsNonExistentAttrIds extends GetAttrsBase {

    public static void main(String[] args) throws Exception {
        new GetAttrsNonExistentAttrIds().run(args);
    }

    @Override public void runTest() throws Exception {
        initContext();
        Attributes retAttrs = getAttributes();
        verifyAttributes(retAttrs);
    }

    /*
     * Tests that we can get the attributes of a DNS entry.
     * Supply at least one nonexistent attribute name in attrIds
     * (should be ignored).
     */
    @Override public Attributes getAttributes() throws Exception {
        String[] attrIds = new String[getMandatoryAttrs().length + 1];
        attrIds[0] = "SOA";
        System.arraycopy(getMandatoryAttrs(), 0, attrIds, 1,
                getMandatoryAttrs().length);

        return context().getAttributes(getKey(), attrIds);
    }
}
