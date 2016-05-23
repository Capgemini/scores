package com.capgemini.scores.league.aggregate.service;

import com.capgemini.scores.message.Event;

/**
 * A fine grained event publisher that publishes a single event for a specific event class.
 *
 * @param <T> The event class
 */
public interface FineGrainedEventPublisher<T extends Event> {

    /**
     * Publishes an event
     *
     * @param event The event
     */
    void publish(T event);

    /**
     *
     * @return The event class associated with this publisher
     */
    Class<T> getEventClass();
}
