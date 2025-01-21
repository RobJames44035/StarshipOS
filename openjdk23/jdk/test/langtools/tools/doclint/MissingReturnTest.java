/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/** . */
public class MissingReturnTest {
    /** no return allowed */
    MissingReturnTest() { }

    /** no return allowed */
    void return_void() { }

    /** no return required */
    Void return_Void() { }

    /** . */
    int missingReturn() { }
}
