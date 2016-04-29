package com.capgemini.scores.league.message;

import com.capgemini.scores.league.domain.LeagueTable;

public class CreateLeagueTableCommand implements Command<LeagueTable> {

    private LeagueTable leagueTable;
    
    @Override
    public LeagueTable getPayload() {
        return leagueTable;
    }

}
