package com.capgemini.scores.league.aggregate.service;

import com.capgemini.gregor.KafkaClient;
import com.capgemini.gregor.KafkaProducer;
import com.capgemini.gregor.serializer.JSONSerializer;
import com.capgemini.scores.Topics;
import com.capgemini.scores.league.aggregate.message.MatchResultEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A FineGrainedEventPublisher that publishes match result event messages to Kafka.
 *
 * @author Craig Williams
 */
@Component
public class MatchResultEventKafkaPublisher implements FineGrainedEventPublisher<MatchResultEvent> {

    private MatchResultKafkaProducer producer;

    @Autowired
    public MatchResultEventKafkaPublisher(MatchResultKafkaProducer producer) {
        this.producer = producer;
    }

    @Override
    public void publish(MatchResultEvent event) {
        producer.sendEvent(event);
    }

    @Override
    public Class<MatchResultEvent> getEventClass() {
        return MatchResultEvent.class;
    }

    @KafkaClient
    public interface MatchResultKafkaProducer {

        @KafkaProducer(topic = Topics.MATCH_RESULT_EVENT, payloadSerializer = JSONSerializer.class)
        void sendEvent(MatchResultEvent event);
    }
}
