/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838310
 * @summary This test verifies that the restrictions on proxy interface
 * methods with the same signature but different return types are
 * correctly enforced.
 * @author Peter Jones
 *
 * @build GetObject GetSerializable GetCloneable GetArray
 * @run main Test
 */

import java.lang.reflect.Proxy;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Test {

    // additional test cases may be added to both of these lists:

    private static final Class<?>[][] GOOD = {
        { Collection.class },
        { Iterable.class, Collection.class },
        { Iterable.class, Collection.class, List.class },
        { GetSerializable.class, GetCloneable.class, GetArray.class },
        { GetObject.class, GetSerializable.class, GetCloneable.class,
          GetArray.class }
    };

    private static final Class<?>[][] BAD = {
        { Runnable.class, PrivilegedAction.class },
        { GetSerializable.class, GetCloneable.class },
        { GetObject.class, GetSerializable.class, GetCloneable.class }
    };

    public static void main(String[] args) throws Exception {
        System.err.println("\nRegression test for bug 4838310\n");

        ClassLoader loader = Test.class.getClassLoader();

        System.err.println("Testing GOOD combinations:");

        for (int i = 0; i < GOOD.length; i++) {
            Class<?>[] interfaces = GOOD[i];
            System.err.println(Arrays.asList(interfaces));
            Proxy.getProxyClass(loader, interfaces);
            System.err.println("--- OK.");
        }

        System.err.println("Testing BAD combinations:");

        for (int i = 0; i < BAD.length; i++) {
            Class<?>[] interfaces = BAD[i];
            System.err.println(Arrays.asList(interfaces));
            try {
                Proxy.getProxyClass(loader, interfaces);
                throw new RuntimeException(
                    "TEST FAILED: bad combination succeeded");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.err.println("--- OK.");
            }
        }

        System.err.println("TEST PASSED");
    }
}
