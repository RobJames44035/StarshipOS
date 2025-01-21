/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @summary Verify that ObjectInputStream can skip over unresolvable serialized
 *          proxy instances.
 */

import java.io.*;
import java.lang.reflect.*;

class Handler implements InvocationHandler, Serializable {
    private static final long serialVersionUID = 1L;

    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        return null;
    }
}
