package com.capgemini.scores.league.aggregate.message;

import com.capgemini.scores.league.aggregate.domain.LeagueTable;
import com.capgemini.scores.message.Command;

public class CreateLeagueTableCommand implements Command<LeagueTable> {

    private LeagueTable leagueTable;
    
    @Override
    public LeagueTable getPayload() {
        return leagueTable;
    }

}
