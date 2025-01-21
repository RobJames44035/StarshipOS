/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8269351
 * @run testng SealedInterfaceTest
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class SealedInterfaceTest {
    sealed interface Intf permits NonSealedInterface {
        void m1();
    }

    non-sealed interface NonSealedInterface extends Intf {
        void m2();
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void testSealedInterface() {
        Proxy.newProxyInstance(SealedInterfaceTest.class.getClassLoader(),
                new Class<?>[]{ Intf.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
    }

    @Test
    public void testNonSealedInterface() {
        Proxy.newProxyInstance(SealedInterfaceTest.class.getClassLoader(),
                new Class<?>[]{ NonSealedInterface.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
    }
}
