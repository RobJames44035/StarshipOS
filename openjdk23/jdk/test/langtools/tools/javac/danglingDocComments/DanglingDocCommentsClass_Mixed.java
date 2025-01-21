/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

// This is a test of duplicate doc comments in a class, using a mixture of traditional and end-of-line comments
// It is a useful test case for a real-world scenario in which an end-of-line comment is
// placed between a traditional doc comment and its declaration.

/** First Class Comment. */
/// Second Class Comment.
@Deprecated public class DanglingDocCommentsClass_Mixed
    {
    /** First Field Comment. */
    /// Second Field Comment.
    public int i;

    /** First Method Comment. */
    /// Second Method Comment.
    public void m1() { }
}