/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 8198882
 * @summary Tests that we can get the attributes of a DNS entry.
 *          Use getAttributes form that has no attrIds parameter.
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetAttrs
 */

import javax.naming.directory.Attributes;

public class GetAttrs extends GetAttrsBase {

    public static void main(String[] args) throws Exception {
        new GetAttrs().run(args);
    }

    @Override public void runTest() throws Exception {
        initContext();
        Attributes retAttrs = getAttributes();
        verifyAttributes(retAttrs);
    }

    /*
     * Tests that we can get the attributes of a DNS entry.
     * Use getAttributes form that has no attrIds parameter.
     */
    @Override public Attributes getAttributes() throws Exception {
        return context().getAttributes(getKey());
    }
}
