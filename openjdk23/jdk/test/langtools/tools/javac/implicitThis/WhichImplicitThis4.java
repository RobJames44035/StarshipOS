/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4696701
 * @summary wrong enclosing instance for local class creation
 *
 * @compile WhichImplicitThis4.java
 * @run main WhichImplicitThis4
 */

public class WhichImplicitThis4 {
    boolean isCorrect() { return true; }
    void check() {
        class I2 {
            I2() {
                if (!isCorrect()) throw new Error();
            }
        }
        class I3 extends WhichImplicitThis4 {
            boolean isCorrect() { return false; }
            public void check() {
                new I2() {
                        /*
                          <init>() {
                            (WhichImplicitThis4.this).super();
                          }
                         */
                };
            }
        }
        new I3().check();
    }
    public static void main(String[] args) {
        new WhichImplicitThis4().check();
    }
}
