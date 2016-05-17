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

package com.capgemini.scores.gateway.domain;

/**
 * Match result domain object.
 * 
 * @author craigwilliams84
 *
 */
public class MatchResult {
    
    String competitionId;
    
    String homeTeam;
    
    int homeScore;
    
    String awayTeam;
    
    int awayScore;
    
    public MatchResult() {
        
    }

    public MatchResult(String competitionId, String homeTeam, int homeScore, String awayTeam, int awayScore) {
        this.competitionId = competitionId;
        this.homeTeam = homeTeam;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.awayScore = awayScore;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchResult that = (MatchResult) o;

        if (getHomeScore() != that.getHomeScore()) return false;
        if (getAwayScore() != that.getAwayScore()) return false;
        if (getCompetitionId() != null ? !getCompetitionId().equals(that.getCompetitionId()) : that.getCompetitionId() != null)
            return false;
        if (getHomeTeam() != null ? !getHomeTeam().equals(that.getHomeTeam()) : that.getHomeTeam() != null)
            return false;
        return getAwayTeam() != null ? getAwayTeam().equals(that.getAwayTeam()) : that.getAwayTeam() == null;

    }

    @Override
    public int hashCode() {
        int result = getCompetitionId() != null ? getCompetitionId().hashCode() : 0;
        result = 31 * result + (getHomeTeam() != null ? getHomeTeam().hashCode() : 0);
        result = 31 * result + getHomeScore();
        result = 31 * result + (getAwayTeam() != null ? getAwayTeam().hashCode() : 0);
        result = 31 * result + getAwayScore();
        return result;
    }
}
