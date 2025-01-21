/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/* @test
 * @bug 4413615
 * @summary Verify that ObjectStreamClass.lookupAny() returns a non-null
 *          descriptor for class which doesn't implement java.io.Serializable
 *          interface at all.
 */

import java.io.*;

class Foo {
}

public class LookupAnyInvocation {
    public static final void main(String[] args) {
        ObjectStreamClass descs = ObjectStreamClass.lookupAny(Foo.class);
        if (descs == null) {
            throw new Error();
        }
    }
}
