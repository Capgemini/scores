package com.capgemini.scores.league.aggregate.service;

import com.capgemini.scores.message.Command;
import com.capgemini.scores.message.Event;
import com.capgemini.scores.league.aggregate.message.MatchResultCommand;
import com.capgemini.scores.league.aggregate.repository.LeagueTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.scores.league.aggregate.domain.LeagueTable;
import com.capgemini.scores.league.aggregate.message.CreateLeagueTableCommand;

import java.util.List;

@Component
public class DefaultLeagueTableService implements LeagueTableService {

    private LeagueTableRepository repository;

    private EventPublisherService eventPublisherService;
    
    @Autowired
    public DefaultLeagueTableService(LeagueTableRepository repository, EventPublisherService eventPublisherService) {
        this.repository = repository;
        this.eventPublisherService = eventPublisherService;
    }

    @Override
    public void onMatchResultCommand(MatchResultCommand command) {
        processAndPublish(command, repository.getLeagueTable(command.getPayload().getCompetitionId()));
    }

    @Override
    public void onCreateLeagueTableCommand(CreateLeagueTableCommand command) {
        processAndPublish(command, new LeagueTable());
    }

    private void processAndPublish(Command<?> command, LeagueTable leagueTable) {
        final List<Event> events = leagueTable.process(command);
        repository.save(leagueTable);

        publishEvents(events);
    }

    private void publishEvents(List<Event> events) {
        eventPublisherService.publish(events);
    }

}
