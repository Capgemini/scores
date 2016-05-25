package com.capgemini.scores.league.view;

import com.capgemini.scores.Topics;
import com.capgemini.scores.league.view.message.MatchResultEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.gregor.KafkaConsumer;
import com.capgemini.gregor.PayloadContent;

/**
 * Consumes match result messages from a kafka broker.
 * 
 * Utilises the gregor library annotations.
 * 
 * @author craigwilliams84
 *
 */
@Component
public class KafkaEventConsumer {

    LeagueTableUpdater leagueUpdater;
    
    @Autowired
    public KafkaEventConsumer(LeagueTableUpdater leagueUpdater) {
        this.leagueUpdater = leagueUpdater;
    }
    
    @KafkaConsumer(topic = Topics.MATCH_RESULT_EVENT, payloadContent = PayloadContent.JSON)
    public void onMatchResultEvent(MatchResultEvent matchResultEvent) {
        leagueUpdater.updateTable(matchResultEvent.getPayload());
    }
}
