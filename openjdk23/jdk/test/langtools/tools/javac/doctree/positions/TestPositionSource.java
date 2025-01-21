/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

public class TestPositionSource {

    /**First sentence.
     *
     * <p>Description with {@link java.io.InputStream link}
     *
     * <em>text &lt; &#60; <!--some comment--> suffix</em>
     *
     * @param first description
     * @param second description
     * @return whatever
     * @throws IllegalStateException why?
     * @since 1.15
     * @see java.util.List
     */
    public boolean valid(int first, int second) throws IllegalStateException {
        return true;
    }

    /**First sentence.
     *
     * <p>Description with {@link}, {@link java.util.List}, {@link
     *
     * @param
     * @param second
     * @return
     * @throws
     * @throws IllegalStateException
     * @since
     * @see
     */
    public boolean erroneous(int first, int second) throws IllegalStateException {
        return true;
    }

    /**First sentence. <em  >text <!--  some comment  --> suffix</em  >
     *
     * <p>Description with {@link    }, {@link java.util.List#add(   int   )},
     * {@link java.util.List#add(   int   ) some   text   with   whitespaces}, {@link
     *
     * @param     first
     * @param     second   some   text   with trailing whitespace
     * @return      some   return
     * @throws      java.lang.IllegalStateException
     * @throws   java.lang.IllegalStateException some     text
     */
    public boolean withWhiteSpaces(int first, int second) throws IllegalStateException {
        return true;
    }

    /**First sentence.
     *
     * <p>Description with {@unknownInlineTag }, {@unknownInlineTag text}
     *
     * @param p1 p {@unknownInlineTag text}
     * @param p2 p <
     * @param p3 p <em
     * @param p4 p <!--
     * @param p5 p <!--  --
     * @param p6 p <!--  --
     * @param p7 p &
     * @param p8 p &lt
     * @param p9 p <em> </
     * @param pa p <em> </em
     */
    public void erroneous2(int p1, int p2, int p3, int p4, int p5,
                           int p6, int p7, int p8, int p9, int pa) {
    }
}
