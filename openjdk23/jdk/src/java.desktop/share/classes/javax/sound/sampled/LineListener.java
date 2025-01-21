/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package javax.sound.sampled;

import java.util.EventListener;

/**
 * Instances of classes that implement the {@code LineListener} interface can
 * register to receive events when a line's status changes.
 *
 * @author Kara Kytle
 * @see Line
 * @see Line#addLineListener
 * @see Line#removeLineListener
 * @see LineEvent
 * @since 1.3
 */
public interface LineListener extends EventListener {

    /**
     * Informs the listener that a line's state has changed. The listener can
     * then invoke {@code LineEvent} methods to obtain information about the
     * event.
     *
     * @param  event a line event that describes the change
     */
    void update(LineEvent event);
}
