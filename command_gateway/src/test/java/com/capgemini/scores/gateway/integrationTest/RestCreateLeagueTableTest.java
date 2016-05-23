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

package com.capgemini.scores.gateway.integrationTest;

import com.capgemini.scores.Topics;
import com.capgemini.scores.gateway.CommandGatewayApplication;
import com.capgemini.scores.gateway.domain.LeagueTable;
import com.capgemini.scores.gateway.domain.MatchResult;
import com.capgemini.scores.gateway.message.CreateLeagueTableCommand;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({CommandGatewayApplication.class})
@WebIntegrationTest({"kafka.addresses=localhost:" + BaseKafkaTest.BROKER_PORT,
        "zookeeper.address=localhost:" + BaseKafkaTest.ZOOKEEPER_PORT})
public class RestCreateLeagueTableTest extends BaseKafkaTest {

    @Test
    public void testCreateLeagueTable() throws TimeoutException {
        final String uri = "http://localhost:8080/league";

        final LeagueTable leagueTable = new LeagueTable("Test League",
                new HashSet<String>(Arrays.asList(new String[]{"Tottenham Hotspur", "Arsenal"})));
        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<Void> response = restTemplate.exchange(uri,
                HttpMethod.PUT, new HttpEntity<LeagueTable>(leagueTable), Void.class);

        assertEquals("Incorrect response code", HttpStatus.ACCEPTED, response.getStatusCode());

        final List<String> messages = readMessages(Topics.CREATE_LEAGUE_TABLE_COMMAND, 1);

        final CreateLeagueTableCommand publishedCommand =
                new Gson().fromJson(messages.get(0), CreateLeagueTableCommand.class);

        final LeagueTable publishedTable = publishedCommand.getPayload();

        assertEquals("Published league name incorrect", "Test League", publishedTable.getName());
        final Iterator<String> teamsIterator = publishedTable.getTeams().iterator();

        assertEquals("First team in league incorrect", "Tottenham Hotspur", teamsIterator.next());
        assertEquals("Second team in league incorrect", "Arsenal", teamsIterator.next());
    }
}
