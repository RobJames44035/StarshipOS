/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package pkg1;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * A class comment for testing.
 *
 * @see C1
 * @since       JDK1.0
 */

public class C2 implements Serializable {

    /**
     * This field indicates title.
     */
    String title;

    public static enum ModalType {
        NO_EXCLUDE
    };

    /**
     * Constructor.
     *
     */
     public C2() {

     }

     public C2(String title) {

     }

     /**
     * Set visible.
     *
     * @param set boolean
     * @since 1.4
     * @deprecated As of JDK version 1.5, replaced by
     * {@link C1#setUndecorated(boolean) setUndecorated(boolean)}.
     */
     @Deprecated
     public void setVisible(boolean set) {
     }

     /**
     * Reads the object stream.
     *
     * @param s ObjectInputStream
     * @throws IOException on error
     * @deprecated As of JDK version 1.5, replaced by
     * {@link C1#setUndecorated(boolean) setUndecorated(boolean)}.
     */
     @Deprecated
     public void readObject(ObjectInputStream s) throws IOException {
     }
}
