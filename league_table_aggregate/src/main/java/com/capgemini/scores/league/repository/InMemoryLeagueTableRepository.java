package com.capgemini.scores.league.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.capgemini.scores.league.domain.LeagueTable;

@Component
public class InMemoryLeagueTableRepository implements LeagueTableRepository {

    private Map<String, LeagueTable> tableMap;
    
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

}
