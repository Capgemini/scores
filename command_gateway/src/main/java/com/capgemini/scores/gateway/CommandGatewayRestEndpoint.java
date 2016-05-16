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
import com.capgemini.scores.gateway.integration.CommandPublisher;
import com.capgemini.scores.gateway.message.CreateLeagueTableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public void createLeague(@RequestBody LeagueTable table) {
        final CreateLeagueTableCommand command = new CreateLeagueTableCommand(table);
        commandPublisher.publishCreateLeagueTableCommand(command);
    }
}
