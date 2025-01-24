/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.eventapi

/**
 * Represents a message that can be used in the event system.
 *
 * <p>This class encapsulates details such as sender, recipient, topic, payload,
 * and other properties necessary for message-based communication in the 
 * Starship Event API.</p>
 *
 * <ul>
 *   <li><b>id</b>: Unique message ID</li>
 *   <li><b>correlationId</b>: ID used to correlate responses like ACK/NACK</li>
 *   <li><b>senderId</b>: ID of the message sender</li>
 *   <li><b>recipientId</b>: (Optional) Specific recipient of the message</li>
 *   <li><b>topic</b>: (Optional) Topic associated with the message</li>
 *   <li><b>payload</b>: The data or content carried by the message</li>
 *   <li><b>replyTo</b>: (Optional) Address for the response delivery</li>
 * </ul>
 *
 * <p>The class also provides a static builder method to construct messages
 * using a Groovy Closure for ease of configuration.</p>
 *
 * @author R.A. James
 * @since 2025
 */
class EventMessage {
    String id           // Unique message ID
    String correlationId // Correlation ID for responses (ACK/NACK)
    String senderId      // ID of the sender
    String recipientId   // (Optional) Targeted recipient
    String topic         // (Optional) Topic for message
    Object payload       // Actual message data
    String replyTo       // (Optional) Reply-to for responses


    /**
    * Static builder method to create an instance of {@link EventMessage}.
    *
    * <p>This method uses a Groovy {@link Closure} to configure the
    * properties of the {@link EventMessage} instance. It creates a new
    * instance of {@link EventMessage}, sets the closure's delegate to this
    * instance, and executes the provided closure.</p>
    *
    * <p>Example usage:</p>
    * <pre>{@code
    * EventMessage message = EventMessage.build {
    *     id = "12345"
    *     senderId = "system"
    *     recipientId = "user1"
    *     topic = "chat.message"
    *     payload = "Hello, World!"
    *     replyTo = "/reply/endpoint"
    * }
    *}</pre>
    *
    * @param closure The {@link Closure} to configure the {@link EventMessage}.
    * @return A configured instance of {@link EventMessage}.
    */
    static EventMessage build(Closure closure) {
        EventMessage message = new EventMessage()
        closure.delegate = message
        closure()
        return message
    }
}