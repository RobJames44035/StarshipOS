/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class MisleadingErrorMsgDiamondPlusPrivateCtorTest {
    public void foo() {
        MyClass<Object> foo = new MyClass<>();
    }
}

class MyClass<E> {
    private MyClass() {}
}
