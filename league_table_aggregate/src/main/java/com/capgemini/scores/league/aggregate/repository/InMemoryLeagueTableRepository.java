package com.capgemini.scores.league.aggregate.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.capgemini.scores.league.aggregate.domain.LeagueTable;

@Component
public class InMemoryLeagueTableRepository implements LeagueTableRepository {

    private Map<String, LeagueTable> tableMap = new HashMap<String, LeagueTable>();
    
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
