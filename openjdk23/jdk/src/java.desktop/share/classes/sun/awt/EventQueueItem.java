/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package sun.awt;

import java.awt.AWTEvent;

public class EventQueueItem {
    public AWTEvent event;
    public EventQueueItem next;

    public EventQueueItem(AWTEvent evt) {
        event = evt;
    }
}
