/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.eventapi

class EventMessage {
    String id           // Unique message ID
    String correlationId // Correlation ID for responses (ACK/NACK)
    String senderId      // ID of the sender
    String recipientId   // (Optional) Targeted recipient
    String topic         // (Optional) Topic for message
    Object payload       // Actual message data
    String replyTo       // (Optional) Reply-to for responses

    static EventMessage build(Closure closure) {
        EventMessage message = new EventMessage()
        closure.delegate = message
        closure()
        return message
    }
}