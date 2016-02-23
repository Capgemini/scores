package com.capgemini.scores.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.gregor.KafkaConsumer;
import com.capgemini.gregor.PayloadContent;
import com.capgemini.scores.league.domain.MatchResult;

/**
 * Consumes match result messages from a kafka broker.
 * 
 * Utilises the gregor library annotations.
 * 
 * @author craigwilliams84
 *
 */
@Component
public class MatchResultConsumer {

    LeagueTableUpdater leagueUpdater;
    
    @Autowired
    public MatchResultConsumer(LeagueTableUpdater leagueUpdater) {
        this.leagueUpdater = leagueUpdater;
    }
    
    @KafkaConsumer(topic = Topics.MATCH_RESULT, payloadContent = PayloadContent.JSON)
    public void onMatchResult(MatchResult matchResult) {
        leagueUpdater.updateTable(matchResult);
    }
}
