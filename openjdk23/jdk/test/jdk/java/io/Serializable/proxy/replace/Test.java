/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @summary Ensure that serialization invokes writeReplace/readResolve methods
 *          on dynamic proxies, just as with normal objects.
 */

import java.io.*;
import java.lang.reflect.*;

public class Test implements InvocationHandler, Serializable {
    private static final long serialVersionUID = 1L;

    static ClassLoader loader = Test.class.getClassLoader();

    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        String methName = method.getName();
        if (methName.equals("writeReplace")) {
            return Proxy.newProxyInstance(
                loader, new Class<?>[] { ReadResolve.class }, this);
        } else if (methName.equals("readResolve")) {
            return Proxy.newProxyInstance(
                loader, new Class<?>[] { Resolved.class }, this);
        } else if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        } else {
            throw new Error();
        }
    }

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(Proxy.newProxyInstance(
            loader, new Class<?>[] { WriteReplace.class }, new Test()));
        oout.close();
        ObjectInputStream oin = new ObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        if (!(oin.readObject() instanceof Resolved)) {
            throw new Error();
        }
    }
}
