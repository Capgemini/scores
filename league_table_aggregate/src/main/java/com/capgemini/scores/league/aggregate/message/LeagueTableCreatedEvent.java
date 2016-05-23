package com.capgemini.scores.league.aggregate.message;

import com.capgemini.scores.league.aggregate.domain.LeagueTable;
import com.capgemini.scores.message.Event;

public class LeagueTableCreatedEvent implements Event<LeagueTable> {

    private LeagueTable leagueTable;
    
    public LeagueTableCreatedEvent(LeagueTable leagueTable) {
        this.leagueTable = leagueTable;
    }
    
    @Override
    public LeagueTable getPayload() {
        return leagueTable;
    }

}
