package com.capgemini.scores.league.aggregate;

import com.capgemini.scores.Topics;
import com.capgemini.scores.league.aggregate.message.MatchResultCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.gregor.KafkaConsumer;
import com.capgemini.gregor.PayloadContent;
import com.capgemini.scores.league.aggregate.message.CreateLeagueTableCommand;
import com.capgemini.scores.league.aggregate.service.LeagueTableService;

/**
 * Consumes match result messages from a kafka broker.
 * 
 * Utilises the gregor library annotations.
 * 
 * @author craigwilliams84
 *
 */
@Component
public class KafkaCommandConsumer {

    private LeagueTableService leagueTableService;
    
    @Autowired
    public KafkaCommandConsumer(LeagueTableService leagueTableService) {
        this.leagueTableService = leagueTableService;
    }
    
    @KafkaConsumer(topic = Topics.MATCH_RESULT_COMMAND, payloadContent = PayloadContent.JSON)
    public void onMatchResultCommand(MatchResultCommand command) {
        System.out.println("**** Received match result command");
        leagueTableService.onMatchResultCommand(command);
    }
    
    @KafkaConsumer(topic = Topics.CREATE_LEAGUE_TABLE_COMMAND, payloadContent = PayloadContent.JSON)
    public void onCreateLeagueTableCommand(CreateLeagueTableCommand command) {
        
        leagueTableService.onCreateLeagueTableCommand(command);
    }
}
