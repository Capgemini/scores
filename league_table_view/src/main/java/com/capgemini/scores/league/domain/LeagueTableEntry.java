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

package com.capgemini.scores.league.domain;

/**
 * League table entry domain object.
 * 
 * Represents a teams current performance in an associated league.
 * 
 * @author craigwilliams84
 *
 */
public class LeagueTableEntry {
    
    private String teamName;
    
    private int totalPoints = 0;
    
    private LeagueTeamStatistics statistics;
    
    public LeagueTableEntry(String teamName, int totalPoints, LeagueTeamStatistics statistics) {
        this.teamName = teamName;
        this.totalPoints = totalPoints;
        this.statistics = statistics;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public LeagueTeamStatistics getStatistics() {
        return statistics;
    }

    public void updatePoints(int delta) {
        totalPoints = totalPoints + delta;
    }
}
