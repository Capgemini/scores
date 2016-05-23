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

package com.capgemini.scores.gateway.message;

import com.capgemini.scores.gateway.domain.LeagueTable;
import com.capgemini.scores.message.Command;

/**
 * A command instructing a league table to created
 */
public class CreateLeagueTableCommand implements Command<LeagueTable> {

    private LeagueTable leagueTable;

    public CreateLeagueTableCommand(LeagueTable leagueTable) {
        this.leagueTable = leagueTable;
    }
    
    @Override
    public LeagueTable getPayload() {
        return leagueTable;
    }

}
