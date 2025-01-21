/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

/**
 *
 * Defines char-to-key binding. The generation of a symbol will, in general,
 * require modifier keys to be pressed prior to pressing a primary key. Classes
 * that implement {@code CharBindingMap} communicate what modifiers and
 * primary key are required to generate a given symbol.
 *
 * @see org.netbeans.jemmy.DefaultCharBindingMap
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public interface CharBindingMap {

    /**
     * Returns the code of the primary key used to type a symbol.
     *
     * @param c Symbol code.
     * @return a key code.
     * @see java.awt.event.InputEvent
     */
    public int getCharKey(char c);

    /**
     * Returns the modifiers that should be pressed to type a symbol.
     *
     * @param c Symbol code.
     * @return a combination of InputEvent MASK fields.
     * @see java.awt.event.InputEvent
     */
    public int getCharModifiers(char c);
}
