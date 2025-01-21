/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/** . */
public class MissingParamsTest {
    /** . */
    MissingParamsTest(int param) { }

    /** . */
    <T> MissingParamsTest() { }

    /** . */
    void missingParam(int param) { }

    /** . */
    <T> void missingTyparam() { }

    /** . */
    public class MissingTyparam<T> { /** . */ MissingTyparam() { } }
}
