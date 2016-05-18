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

package com.capgemini.scores.gateway;

import com.capgemini.scores.gateway.domain.LeagueTable;
import com.capgemini.scores.gateway.domain.MatchResult;
import com.capgemini.scores.gateway.integration.CommandPublisher;
import com.capgemini.scores.gateway.message.CreateLeagueTableCommand;
import com.capgemini.scores.gateway.message.MatchResultCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST request command endpoint
 */
@RestController
public class CommandGatewayRestEndpoint {

    private CommandPublisher commandPublisher;

    @Autowired
    public CommandGatewayRestEndpoint(CommandPublisher commandPublisher) {
        this.commandPublisher = commandPublisher;
    }

    @RequestMapping(path="/league", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createLeague(@RequestBody LeagueTable table) {
        final CreateLeagueTableCommand command = new CreateLeagueTableCommand(table);
        commandPublisher.publishCreateLeagueTableCommand(command);
    }

    @RequestMapping(path="/league/{leagueName}/result", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createMatchResult(@RequestBody MatchResult result, @PathVariable String leagueName) {
        result.setCompetitionId(leagueName);
        final MatchResultCommand command = new MatchResultCommand(result);
        commandPublisher.publishMatchResultCommand(command);
    }
}
