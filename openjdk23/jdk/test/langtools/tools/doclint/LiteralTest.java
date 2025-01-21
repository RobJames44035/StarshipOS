/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/** . */
public class LiteralTest {
    /** <code> abc {@literal < & > } def </code> */
    public void ok_literal_in_code() { }

    /** <code> abc {@code < & > } def </code> */
    public void bad_code_in_code() { }

    /** . */
    LiteralTest() { }
}
