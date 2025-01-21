/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package javax.swing.event;


import java.util.EventListener;

/**
 * HyperlinkListener
 *
 * @author  Timothy Prinzing
 */
public interface HyperlinkListener extends EventListener {

    /**
     * Called when a hypertext link is updated.
     *
     * @param e the event responsible for the update
     */
    void hyperlinkUpdate(HyperlinkEvent e);
}
