package com.capgemini.scores.league.aggregate.service;

import com.capgemini.scores.league.aggregate.message.Event;

import java.util.List;

/**
 * A service that provides methods in order to publish events to the wider system.
 *
 * @author craigwilliams84
 */
public interface EventPublisherService {

    /**
     * Publishes events (of varying types).
     *
     * @param events The events to publish
     */
    void publish(List<Event> events);
}
