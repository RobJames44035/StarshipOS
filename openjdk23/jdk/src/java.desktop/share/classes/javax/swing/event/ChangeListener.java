/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package javax.swing.event;


import java.util.EventListener;


/**
 * Defines an object which listens for ChangeEvents.
 *
 * @author Jeff Dinkins
 */
public interface ChangeListener extends EventListener {
    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e  a ChangeEvent object
     */
    void stateChanged(ChangeEvent e);
}
