package com.capgemini.scores.league.aggregate.service;

import com.capgemini.scores.league.aggregate.message.MatchResultCommand;
import com.capgemini.scores.league.aggregate.message.CreateLeagueTableCommand;

public interface LeagueTableService {
    
    void onMatchResultCommand(MatchResultCommand command);
    
    void onCreateLeagueTableCommand(CreateLeagueTableCommand command);
}
