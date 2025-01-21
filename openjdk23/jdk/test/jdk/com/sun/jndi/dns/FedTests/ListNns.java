/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8210339
 * @summary Test that we can List the nns of a DNS entry.
 * @library ../lib/
 * @modules java.naming/com.sun.jndi.toolkit.dir
 *          java.base/sun.security.util
 * @build FedSubordinateNs FedObjectFactory
 * @run main/othervm ListNns
 */

public class ListNns extends ListFedBase {

    private static final int COUNT_LIMIT = 2; // a, x - 2 entries

    public static void main(String[] args) throws Exception {
        new ListNns().run(args);
    }

    /*
     * Test that we can List the nns of a DNS entry.
     */
    @Override
    public void runTest() throws Exception {
        env().put(Context.OBJECT_FACTORIES, "FedObjectFactory");
        setContext(new InitialDirContext(env()));

        NamingEnumeration<Binding> enumObj = context().listBindings(getKey() + "/");
        verifyNamingEnumeration(enumObj, COUNT_LIMIT);
    }
}
