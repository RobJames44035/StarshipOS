/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package pkg1;

import java.io.IOException;
import java.io.Serializable;

/**
 * A class comment for testing.
 *
 * @since       JDK1.0
 */
public class C1 implements Serializable {

    /**
     * This field indicates whether the C1 is undecorated.
     *
     * @see #setUndecorated(boolean)
     * @since 1.4
     * @serial
     * @deprecated As of JDK version 1.5, replaced by
     * {@link C1#setUndecorated(boolean) setUndecorated(boolean)}.
     */
    @Deprecated
    public boolean undecorated = false;

    /**
     * This enum specifies the possible modal exclusion types.
     *
     * @since 1.6
     */
    public static enum ModalExclusionType {

        /**
         * No modal exclusion.
         */
        NO_EXCLUDE,
        /**
         * <code>APPLICATION_EXCLUDE</code> indicates that a top-level window
         * won't be blocked by any application-modal dialogs. Also, it isn't
         * blocked by document-modal dialogs from outside of its child hierarchy.
         */
        APPLICATION_EXCLUDE
    };

    /**
     * Constructor.
     *
     * @param title the title
     * @param test boolean value
     * @exception IllegalArgumentException if the <code>owner</code>'s
     *     <code>GraphicsConfiguration</code> is not from a screen device
     */
    public C1(String title, boolean test) {
    }

    public C1(String title) {
    }

    /**
     * Method comments.
     * @param  undecorated <code>true</code> if no decorations are
     *         to be enabled;
     *         <code>false</code> if decorations are to be enabled.
     * @see    #readObject()
     * @since 1.4
     */
    public void setUndecorated(boolean undecorated) {
        /* Make sure we don't run in the middle of peer creation.*/
    }

    /**
     * @throws java.io.IOException on error
     * @see #setUndecorated(boolean)
     */
    public void readObject() throws IOException {
    }
}
