package com.capgemini.scores.league.aggregate.message;

import com.capgemini.scores.league.aggregate.domain.LeagueTable;

public class CreateLeagueTableCommand implements Command<LeagueTable> {

    private LeagueTable leagueTable;
    
    @Override
    public LeagueTable getPayload() {
        return leagueTable;
    }

}
