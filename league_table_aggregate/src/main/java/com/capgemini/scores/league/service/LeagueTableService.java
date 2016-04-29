package com.capgemini.scores.league.service;

import com.capgemini.scores.league.message.CreateLeagueTableCommand;
import com.capgemini.scores.league.message.MatchResultCommand;

public interface LeagueTableService {
    
    void onMatchResultCommand(MatchResultCommand command);
    
    void onCreateLeagueTableCommand(CreateLeagueTableCommand command);
}
