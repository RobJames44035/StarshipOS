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
 * @summary list of a leaf using a composite name with DNS name as
 *          first component
 * @library ../lib/
 * @modules java.naming/com.sun.jndi.toolkit.dir
 *          java.base/sun.security.util
 * @build FedSubordinateNs FedObjectFactory
 * @run main/othervm ListSubLeaf
 */

public class ListSubLeaf extends ListFedBase {

    private static final int COUNT_LIMIT = 0; // no entry

    public static void main(String[] args) throws Exception {
        new ListSubLeaf().run(args);
    }

    /*
     * list of a leaf using a composite name with DNS name as
     * first component
     */
    @Override
    public void runTest() throws Exception {
        env().put(Context.OBJECT_FACTORIES, "FedObjectFactory");
        setContext(new InitialDirContext(env()));

        NamingEnumeration<Binding> enumObj = context().listBindings(getKey() + "/a/b/c");
        verifyNamingEnumeration(enumObj, COUNT_LIMIT);
    }
}
