package com.capgemini.scores.league.aggregate.service;

import com.capgemini.scores.league.aggregate.message.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event publisher service that delegates to fine grained event publishers.
 *
 * @author craigwilliams84
 */
@Component
public class DelegatingEventPublisherService implements EventPublisherService {

    private Map<Class<? extends Event>, FineGrainedEventPublisher> publishers;

    @Autowired
    public DelegatingEventPublisherService(List<FineGrainedEventPublisher<?>> publishers) {

        this.publishers = new HashMap<Class<? extends Event>, FineGrainedEventPublisher>();

        for (FineGrainedEventPublisher publisher : publishers) {
            this.publishers.put(publisher.getEventClass(), publisher);
        }
    }

    @Override
    public void publish(List<Event> events) {

        for (Event event : events) {
            final FineGrainedEventPublisher publisher = publishers.get(event.getClass());

            if (publisher == null) {
                throw new IllegalArgumentException(
                        "Publisher not found for event class: " + event.getClass().getName());
            }

            publisher.publish(event);
        }
    }
}