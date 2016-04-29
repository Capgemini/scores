package com.capgemini.scores.league.repository;

import java.util.List;

import com.capgemini.scores.league.domain.LeagueTable;

public interface LeagueTableRepository {
    List<LeagueTable> getLeagueTables();
    
    LeagueTable getLeagueTable(String id);
    
    void save(LeagueTable table);
}
