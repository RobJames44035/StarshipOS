/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @summary unicode escapes delimiting and inside of comments
 *
 * @compile UnicodeCommentDelimiter.java
 */

class UnicodeCommentDelimiter {
    public static void main(String[] args) {
        // no error on the following line because although \u005c
        // represents a backslash, that cannot be considered to begin
        // a unicode escape sequence.
        // \u005c000a xyzzy plugh;

        // no error on the following line because there are an even
        // number of backslashes before the u, meaning it is not a
        // unicode escape sequence.
        // \\u000a xyzzy plugh;

        // However, unicode escaped characters can delimit comments.
        \u002f\u002f xyzzy plugh;

        // \u000a class plugh{}
        plugh xyzzy;
    }
}
