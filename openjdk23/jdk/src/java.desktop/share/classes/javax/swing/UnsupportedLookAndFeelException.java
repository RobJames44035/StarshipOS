/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package javax.swing;

/**
 * An exception that indicates the requested look &amp; feel
 * management classes are not present on the user's system.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @since 1.2
 */
@SuppressWarnings("serial") // Same-version serialization only
public class UnsupportedLookAndFeelException extends Exception
{
    /**
     * Constructs an UnsupportedLookAndFeelException object.
     * @param s a message String
     */
    public UnsupportedLookAndFeelException(String s) {
        super(s);
    }
}
