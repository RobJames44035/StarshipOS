/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4346224
 * @summary Test against a typo in ProxyGenerator:
 *          "java/lang/Character" should be used instead of
 *          "java/lang/Char".
 */

import java.lang.reflect.*;

public class CharType {

    public static void main (String args[]) throws Exception {
        Proxy.newProxyInstance(CharMethod.class.getClassLoader(), new Class[] {
            CharMethod.class }, new H());
    }

    static interface CharMethod {
        void setChar(char c);
    }

    static class H implements InvocationHandler {
        public Object invoke(Object o, Method m, Object[] arr) {
            return null;
        }
    }
}
