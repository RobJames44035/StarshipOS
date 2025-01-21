/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4696701
 * @summary wrong enclosing instance for local class creation
 *
 * @compile WhichImplicitThis3.java
 * @run main WhichImplicitThis3
 */

public class WhichImplicitThis3 {
    boolean isCorrect() { return true; }
    void check() {
        class I2 {
            public void check() {
                if (!isCorrect()) throw new Error();
            }
        }
        class I3 extends WhichImplicitThis3 {
            boolean isCorrect() { return false; }
            public void check() {
                new I2().check(); // which outer does I2 get?
            }
        }
        new I3().check();
    }
    public static void main(String[] args) {
        new WhichImplicitThis3().check();
    }
}
