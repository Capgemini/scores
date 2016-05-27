package com.capgemini.scores.league.aggregate.repository;

import java.util.*;

import org.springframework.stereotype.Component;

import com.capgemini.scores.league.aggregate.domain.LeagueTable;

@Component
public class InMemoryLeagueTableRepository implements LeagueTableRepository {

    private Map<String, LeagueTable> tableMap = new HashMap<String, LeagueTable>();

    public InMemoryLeagueTableRepository() {
        final LeagueTable dummyTable = new LeagueTable();
        dummyTable.setId("DummyLeague");

        final Set<String> teams = new HashSet<String>();

        teams.add("Tottenham Hotspur");
        teams.add("Arsenal");

        dummyTable.setTeams(teams);

        tableMap.put("DummyLeague", dummyTable);
    }
    
    @Override
    public List<LeagueTable> getLeagueTables() {
        return new ArrayList<LeagueTable>(tableMap.values());
    }

    @Override
    public LeagueTable getLeagueTable(String id) {
        return tableMap.get(id);
    }
    
    @Override
    public void save(LeagueTable table) {
        tableMap.put(table.getId(), table);
    }

    @Override
    public void delete(LeagueTable tableToDelete) {
        tableMap.remove(tableToDelete.getId());
    }

}
