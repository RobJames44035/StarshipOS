/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8195976
 * @summary Tests that we can get the attributes of a DNS entry using special
 *          qualifiers.
 * @modules java.base/sun.security.util
 * @library ../lib/
 * @run main GetAny
 */

import javax.naming.directory.Attributes;

public class GetAny extends GetAttrsBase {

    public static void main(String[] args) throws Exception {
        new GetAny().run(args);
    }

    @Override public Attributes getAttributes() {
        return null;
    }

    @Override public void runTest() throws Exception {
        initContext();

        // Any type from IN class
        Attributes retAttrs = context()
                .getAttributes(getKey(), new String[] { "*" });
        verifyAttributes(retAttrs);

        retAttrs = context().getAttributes(getKey(), new String[] { "* *" });
        verifyAttributes(retAttrs);

        retAttrs = context().getAttributes(getKey(), new String[] { "IN *" });
        verifyAttributes(retAttrs);
    }
}
