package com.capgemini.scores.league.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.scores.league.domain.LeagueTable;
import com.capgemini.scores.league.message.CreateLeagueTableCommand;
import com.capgemini.scores.league.message.MatchResultCommand;
import com.capgemini.scores.league.repository.LeagueTableRepository;

@Component
public class DefaultLeagueTableService implements LeagueTableService {

    private LeagueTableRepository repository;
    
    @Autowired
    public DefaultLeagueTableService(LeagueTableRepository repository) {
        this.repository = repository;
    }
    @Override
    public void onMatchResultCommand(MatchResultCommand command) {
        final LeagueTable table = repository.getLeagueTable(command.getPayload().getCompetitionId());
        
        table.process(command);       
    }

    @Override
    public void onCreateLeagueTableCommand(CreateLeagueTableCommand command) {
        final LeagueTable table = new LeagueTable();
        
        table.process(command);
        
        repository.save(table);       
    }

}
