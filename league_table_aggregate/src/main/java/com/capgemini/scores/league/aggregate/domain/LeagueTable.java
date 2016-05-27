package com.capgemini.scores.league.aggregate.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.capgemini.scores.domain.ReflectiveAggregate;
import com.capgemini.scores.league.aggregate.exception.TeamNotFoundException;
import com.capgemini.scores.league.aggregate.message.CreateLeagueTableCommand;
import com.capgemini.scores.league.aggregate.message.LeagueTableCreatedEvent;
import com.capgemini.scores.league.aggregate.message.MatchResultCommand;
import com.capgemini.scores.league.aggregate.message.MatchResultEvent;
import com.capgemini.scores.message.Event;

public class LeagueTable extends ReflectiveAggregate {

    private String id;
    
    private Set<String> teams;
    
    private Set<MatchResult> results = new HashSet<MatchResult>();
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getTeams() {
        return teams;
    }

    public void setTeams(Set<String> teams) {
        this.teams = teams;
    }

    public Set<MatchResult> getResults() {
        return results;
    }

    public void setResults(Set<MatchResult> results) {
        this.results = results;
    }
    
    protected List<Event<?>> doProcess(CreateLeagueTableCommand command) {
        
        final LeagueTable leagueTable = command.getPayload();
        
        validateLeagueTable(leagueTable);
        
        id = leagueTable.getId();
        teams = leagueTable.getTeams();
        
        return Arrays.asList(new LeagueTableCreatedEvent(leagueTable));
    }
    
    protected List<Event<?>> doProcess(MatchResultCommand command) {
        
        final MatchResult matchResult = command.getPayload();
        
        validateMatchResult(matchResult);

        matchResult.setCompetitionVersion(getVersion());
        
        results.add(matchResult);
        
        return Arrays.asList(new MatchResultEvent(matchResult));
    }

    private void validateMatchResult(MatchResult matchResult) {
        if (!teams.contains(matchResult.getHomeTeam())) {
            throw new TeamNotFoundException(matchResult.getHomeTeam(), matchResult.getCompetitionId());
        }
        
        if (!teams.contains(matchResult.getAwayTeam())) {
            throw new TeamNotFoundException(matchResult.getAwayTeam(), matchResult.getCompetitionId());
        }
    }
    
    private void validateLeagueTable(LeagueTable leagueTable) {
        //Nothing to do here currently
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeagueTable that = (LeagueTable) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTeams() != null ? !getTeams().equals(that.getTeams()) : that.getTeams() != null) return false;
        return getResults() != null ? getResults().equals(that.getResults()) : that.getResults() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTeams() != null ? getTeams().hashCode() : 0);
        result = 31 * result + (getResults() != null ? getResults().hashCode() : 0);
        return result;
    }
}
