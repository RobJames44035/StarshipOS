/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.eventapi


/**
 * Represents an Event Listener that handles incoming event messages.
 */
interface EventListener {


    /**
    * Handles an incoming event message.
    *
    * @param message the incoming event message to be processed.
    * @param ackHandler a Closure to acknowledge the processing of the event.
    */
    void onEvent(EventMessage message, Closure ackHandler)
}
