/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8025141
 * @summary Ensure the assertion status cannot be changed once the class is initialized
 * @compile CannotChangeAssertionsStateAfterInitialized.java
 * @run main/othervm -da CannotChangeAssertionsStateAfterInitialized
 */

public interface CannotChangeAssertionsStateAfterInitialized {
    default void m() {
        assert false;
    }

    public static void main(String[] args) {
        ClassLoader cl = CannotChangeAssertionsStateAfterInitialized.class.getClassLoader();
        cl.setClassAssertionStatus(CannotChangeAssertionsStateAfterInitialized.class.getName(), true);
        new CannotChangeAssertionsStateAfterInitialized() {}.m();
    }

}
