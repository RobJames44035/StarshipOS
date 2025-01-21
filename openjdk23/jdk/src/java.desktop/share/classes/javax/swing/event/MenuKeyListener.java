/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */
package javax.swing.event;


import java.util.EventListener;


/**
 * MenuKeyListener
 *
 * @author Georges Saab
 */
public interface MenuKeyListener extends EventListener {
    /**
     * Invoked when a key has been typed.
     * This event occurs when a key press is followed by a key release.
     *
     * @param e a {@code MenuKeyEvent}
     */
    void menuKeyTyped(MenuKeyEvent e);

    /**
     * Invoked when a key has been pressed.
     *
     * @param e a {@code MenuKeyEvent}
     */
    void menuKeyPressed(MenuKeyEvent e);

    /**
     * Invoked when a key has been released.
     *
     * @param e a {@code MenuKeyEvent}
     */
    void menuKeyReleased(MenuKeyEvent e);
}
