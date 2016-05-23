package com.capgemini.scores.league.aggregate.service;

import com.capgemini.gregor.KafkaClient;
import com.capgemini.gregor.KafkaProducer;
import com.capgemini.gregor.serializer.JSONSerializer;
import com.capgemini.scores.Topics;
import com.capgemini.scores.league.aggregate.message.LeagueTableCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A FineGrainedEventPublisher that publishes league table created event messages to Kafka.
 *
 * @author Craig Williams
 */
@Component
public class LeagueTableCreatedEventKafkaPublisher implements FineGrainedEventPublisher<LeagueTableCreatedEvent> {

    private LeagueTableCreatedKafkaProducer producer;

    @Autowired
    public LeagueTableCreatedEventKafkaPublisher(LeagueTableCreatedKafkaProducer producer) {
        this.producer = producer;
    }

    @Override
    public void publish(LeagueTableCreatedEvent event) {
        producer.sendEvent(event);
    }

    @Override
    public Class<LeagueTableCreatedEvent> getEventClass() {
        return LeagueTableCreatedEvent.class;
    }

    @KafkaClient
    public interface LeagueTableCreatedKafkaProducer {

        @KafkaProducer(topic = Topics.CREATE_LEAGUE_TABLE_EVENT, payloadSerializer = JSONSerializer.class)
        void sendEvent(LeagueTableCreatedEvent event);
    }
}
