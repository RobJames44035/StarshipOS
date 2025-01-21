/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package pkg2;

public class ClassUseTest1 <T extends Foo & Foo2> {

    public ParamTest<Foo> field;

    public <T extends Foo & Foo2> T method(T t) {
        return null;
    }

}
