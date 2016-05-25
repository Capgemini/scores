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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestLeagueTeamStatistics {

    private LeagueTeamStatistics underTest;
    
    @Before
    public void init() {
        underTest = new LeagueTeamStatistics();
    }
    
    @Test
    public void testUpdateForResultWin() {
        underTest.updateForResult(2, 1);
        
        checkStatistics(1, 0, 0, 2, 1);
    }
    
    @Test
    public void testUpdateForResultDraw() {
        underTest.updateForResult(2, 2);
        
        checkStatistics(0, 1, 0, 2, 2);
    }
    
    @Test
    public void testUpdateForResultLoss() {
        underTest.updateForResult(1, 3);
        
        checkStatistics(0, 0, 1, 1, 3);
    }
    
    private void checkStatistics(int wins, int draws, int losses, int scored, int conceded) {
        assertEquals("Wins incorrect", wins, underTest.getWins());
        assertEquals("Draws incorrect", draws, underTest.getDraws());
        assertEquals("Losses incorrect", losses, underTest.getLosses());
        assertEquals("Goals scored incorrect", scored, underTest.getGoalsScored());
        assertEquals("Goals conceded incorrect", conceded, underTest.getGoalsConceded());
    }
}
