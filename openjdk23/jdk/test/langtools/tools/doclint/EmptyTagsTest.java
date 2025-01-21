/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/** . */
public class EmptyTagsTest { /** . */ EmptyTagsTest() { }
    /**
     * Comment. <p>
     */
    void simpleTrailing() { }

    /**
     * Comment. <p>
     * <ul>
     *     <li>Item.
     * </ul>
     */
    void beforeBlock() { }

    /**
     * Comment. <p>
     * @since 1.0
     */
    void beforeTag() { }

    /**
     * Comment.
     * <ul>
     *     <li>Item. <p>
     * </ul>
     */
    void inBlock() { }

    /**
     * Comment.
     * @author J. Duke<p>
     */
    void inTag() { }
}
