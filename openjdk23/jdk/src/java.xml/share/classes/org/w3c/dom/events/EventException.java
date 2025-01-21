/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.w3c.dom.events;

/**
 *  Event operations may throw an <code>EventException</code> as specified in
 * their method descriptions.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>Document Object Model (DOM) Level 2 Events Specification</a>.
 * @since 1.5, DOM Level 2
 */
public class EventException extends RuntimeException {
    private static final long serialVersionUID = 242753408332692061L;

    public EventException(short code, String message) {
       super(message);
       this.code = code;
    }
    public short   code;
    // EventExceptionCode
    /**
     *  If the <code>Event</code>'s type was not specified by initializing the
     * event before the method was called. Specification of the Event's type
     * as <code>null</code> or an empty string will also trigger this
     * exception.
     */
    public static final short UNSPECIFIED_EVENT_TYPE_ERR = 0;

}
