/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6399361
 * @summary java.lang.Override specification should be revised
 * @author  Gilad Bracha
 * @compile T6399361.java
 */

public interface T6399361 {
    void m();
}

abstract class A implements T6399361 {
}

class B extends A {
    @Override
    public void m() {}
}
