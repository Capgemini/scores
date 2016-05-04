package com.capgemini.scores.league.aggregate.repository;

import java.util.List;

import com.capgemini.scores.league.aggregate.domain.LeagueTable;

public interface LeagueTableRepository {
    List<LeagueTable> getLeagueTables();
    
    LeagueTable getLeagueTable(String id);
    
    void save(LeagueTable table);
}
