/*
* Copyright 2015 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.capgemini.scores.league.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.capgemini.scores.league.view.domain.LeagueTable;
import com.capgemini.scores.league.view.domain.LeagueTableEntry;
import com.capgemini.scores.league.view.domain.LeagueTeamStatistics;
import com.capgemini.scores.league.view.domain.MatchResult;

/**
 * Updates a league table based on a result, with 3 points for a win.
 * 
 * @author craigwilliams84
 *
 */
@Component
public class ThreePointWinLeagueTableUpdater implements LeagueTableUpdater {
    
    private int POINTS_FOR_WIN = 3;
    
    private int POINTS_FOR_DRAW = 1;
    
    private CrudRepository<LeagueTable, String> repository;
    
    @Autowired
    public ThreePointWinLeagueTableUpdater(CrudRepository<LeagueTable, String> repository) {
        this.repository = repository;
    }
    
    /**
     * {@inheritDoc}}
     */
    @Override
    public void updateTable(MatchResult result) {
        final LeagueTable table = repository.findOne(result.getCompetitionId());
        
        if (shouldApplyMatchResult(table, result)) {

            updateTable(table, result);

            repository.save(table);
        } else {
            //TODO Logging
        }
    }

    private boolean shouldApplyMatchResult(LeagueTable table, MatchResult result) {
        if (table.getVersion() > result.getCompetitionVersion()) {
            return false;
        }

        return true;
    }

    private void updateTable(LeagueTable table, MatchResult result) {
        updateTable(table, result.getHomeTeam(), result.getHomeScore(), result.getAwayScore());

        updateTable(table, result.getAwayTeam(), result.getAwayScore(), result.getHomeScore());

        table.setVersion(result.getCompetitionVersion());
    }

    private void updateTable(LeagueTable table, String teamName, int goalsFor, int goalsAgainst) {
        
        final LeagueTableEntry entry = table.findEntry(teamName);
        
        updateScore(entry, goalsFor, goalsAgainst);
        updateStatistics(entry, goalsFor, goalsAgainst);
    }
    
    private void updateScore(LeagueTableEntry entry, int goalsFor, int goalsAgainst) {
        if (goalsFor > goalsAgainst) {
            //Win
            entry.updatePoints(POINTS_FOR_WIN);
        } else if (goalsFor == goalsAgainst) {
            //Draw
            entry.updatePoints(POINTS_FOR_DRAW);
        }
    }
    
    private void updateStatistics(LeagueTableEntry entry, int goalsFor, int goalsAgainst) {
        final LeagueTeamStatistics stats = entry.getStatistics();
        
        stats.updateForResult(goalsFor, goalsAgainst);
    }
}
