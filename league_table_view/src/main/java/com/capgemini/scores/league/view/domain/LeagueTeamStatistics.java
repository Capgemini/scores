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

package com.capgemini.scores.league.view.domain;

/**
 * League team statistics domain object.
 * 
 * Represents the statistics of a team within an associated league.
 * 
 * @author craigwilliams84
 *
 */
public class LeagueTeamStatistics {
    
    private int goalsScored = 0;
    
    private int goalsConceded = 0;
    
    private int wins = 0;
    
    private int draws = 0;
    
    private int losses = 0;
    
    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    /**
     * Update the statistics based on the result of a match.
     * 
     * @param scored The number of goals scored in the match.
     * @param conceded The number of goals conceded in the match
     */
    public void updateForResult(int scored, int conceded) {
        goalsScored += scored;
        goalsConceded += conceded;
        
        if (scored > conceded) {
            wins++;
        } else if (scored == conceded) {
            draws++;
        } else {
            losses++;
        }
    }
}
