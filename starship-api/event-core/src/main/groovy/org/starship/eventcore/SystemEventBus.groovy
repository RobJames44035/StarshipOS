package org.starship.eventcore
/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.starship.eventapi.EventMessage
import org.starship.eventapi.EventListener
import java.util.concurrent.*

/**
 * SystemEventBus is a message bus that facilitates the publishing, subscribing,
 * and delivery of event messages between components in the system. It supports:
 *
 * - Topic-based publish/subscribe model where multiple listeners can subscribe to a topic.
 * - Direct message delivery to specific recipients by their unique IDs.
 * - Acknowledgment callbacks (ACK/NACK/TIMEOUT) with optional timeout handling.
 *
 * Features:
 * - Asynchronous message processing using a cached thread pool.
 * - Timeout scheduling for acknowledgment callbacks using a single-threaded scheduler.
 * - Thread-safe structures for managing subscribers and recipients.
 *
 * Usage:
 * - Subscribe to topics or register recipients using `subscribe` or `register`.
 * - Publish messages with optional acknowledgment handlers and timeout configurations.
 *
 * Example of subscribing to a topic:
 * <pre>{@code
 * EventListener listener = { message, ack -> println("Received: $message"); ack(true) }
 * systemEventBus.subscribe("exampleTopic", listener)
 *}</pre>
 *
 * Example of publishing a message with acknowledgment:
 * <pre>{@code
 * EventMessage message = new EventMessage(id: "msg1", topic: "exampleTopic")
 * systemEventBus.publish(message, { result -> println("Callback result: $result") }, timeoutMs: 1000)
 *}</pre>
 */
@Slf4j
@CompileStatic
class SystemEventBus implements Serializable {

    @SuppressWarnings('unused')
    static final long serialVersionUID = 42L

    Map<String, List<EventListener>> topicSubscribers = [:].withDefault { [] }
    Map<String, EventListener> directRecipients = [:]
    Map<String, Closure> ackCallbacks = [:]

    ExecutorService executorService = Executors.newCachedThreadPool()
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)
    
   /**
    * Publishes an event message to the message bus.
    *
    * Messages can be sent to either a specific recipient or to subscribers of a topic.
    * Optionally, you can provide an acknowledgment callback and a timeout.
    *
    * @param message The event message to be published. Must contain either a topic or recipient ID.
    * @param ackCallback (Optional) A closure to handle acknowledgment responses. Can receive "ACK", "NACK", or "TIMEOUT".
    * @param timeoutMs (Optional) The timeout in milliseconds for acknowledgment. Default is 0 (no timeout).
    */
    void publish(EventMessage message, Closure ackCallback = null, long timeoutMs = 0) {
        if (ackCallback) {
            ackCallbacks[message.id] = ackCallback
            if (timeoutMs > 0) scheduleTimeout(message.id, ackCallback, timeoutMs)
        }

        executorService.submit {
            try {
                deliverMessage(message)
            } catch (Exception e) {
                println "Failed to deliver message: ${e.message}"
            }
        }
    }
    
    /**
    * Internal method to deliver the event message to the appropriate recipient(s).
    *
    * This method checks if the message has a specific recipient ID and delivers it
    * directly if found. If the message has a topic, it will be delivered to all
    * subscribers of that topic. Both delivery methods utilize an acknowledgment
    * handler for handling the response.
    *
    * @param message The event message to be delivered, containing either a recipient ID or a topic.
    */
    private void deliverMessage(EventMessage message) {
        if (message.recipientId) {
            EventListener recipient = directRecipients[message.recipientId]
            if (recipient) {
                recipient.onEvent(message, createAckHandler(message))
            }
        }

        if (message.topic) {
            topicSubscribers[message.topic].each { listener ->
                listener.onEvent(message, createAckHandler(message))
            }
        }
    }

    /**
    * Creates an acknowledgment handler for the given message.
    *
    * This handler will invoke the corresponding acknowledgment callback (if present)
    * with either an "ACK" (for success) or "NACK" (for failure) response.
    *
    * @param message The event message for which the acknowledgment handler is created.
    * @return A closure that processes acknowledgment responses.
    */
    private Closure createAckHandler(EventMessage message) {
        return { boolean success ->
            Closure callback = ackCallbacks.remove(message.id)
            if (callback) {
                callback.call(success ? "ACK" : "NACK")
            }
        }
    }
    
    /**
    * Schedules a timeout for an acknowledgment callback of a specific message.
    *
    * This method sets up a scheduled task that triggers the acknowledgment callback
    * with a "TIMEOUT" response if it is not completed within the specified time limit.
    *
    * @param messageId The unique identifier of the message for which the timeout is scheduled.
    * @param ackCallback The acknowledgment callback to be called upon timeout.
    * @param timeoutMs The timeout duration in milliseconds.
    */
    private void scheduleTimeout(String messageId, Closure ackCallback, long timeoutMs) {
        scheduler.schedule({
            Closure callback = ackCallbacks.remove(messageId)
            if (callback) callback.call("TIMEOUT")
        }, timeoutMs, TimeUnit.MILLISECONDS)
    }

    /**
    * Subscribes an event listener to a specific topic.
    *
    * The listener will be invoked whenever messages are published to the specified topic.
    *
    * @param topic The topic to subscribe to.
    * @param listener The event listener to be notified of messages related to the topic.
    */
    void subscribe(String topic, EventListener listener) {
        topicSubscribers[topic] << listener
    }

    /**
    * Registers an event listener to a specific recipient ID.
    *
    * The listener will handle messages that are addressed directly to the specified recipient ID.
    *
    * @param id The unique identifier of the recipient.
    * @param listener The event listener to handle messages for the recipient.
    */
    void register(String id, EventListener listener) {
        directRecipients[id] = listener
    }

    /**
    * Unregisters an event listener associated with a specific recipient ID.
    *
    * After calling this method, the listener will no longer receive messages
    * addressed directly to the specified recipient ID.
    *
    * @param id The unique identifier of the recipient to be unregistered.
    */
    void unsubscribe(String topic, EventListener listener) {
        topicSubscribers[topic]?.remove(listener)
    }

    /**
    * Unregisters an event listener associated with a specific recipient ID.
    *
    * After calling this method, the listener will no longer receive messages
    * addressed directly to the specified recipient ID.
    *
    * @param id The unique identifier of the recipient to be unregistered.
    */
    void unregister(String id) {
        directRecipients.remove(id)
    }
}
