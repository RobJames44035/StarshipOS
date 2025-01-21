/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.internal;

import jdk.test.internal.foo.*;
import p.two.Bar;

public class RImpl implements R {
    public Foo foo() {
        return new Foo();
    }
    public void throwException() throws FooException {
        throw new FooException();
    }
    public void setBarArray(Bar[][] array) {
    }
}
