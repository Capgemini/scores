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

import com.capgemini.scores.gateway.CommandGatewayApplication;
import com.capgemini.scores.gateway.Topics;
import com.capgemini.scores.gateway.domain.LeagueTable;
import com.capgemini.scores.gateway.domain.MatchResult;
import com.capgemini.scores.gateway.message.CreateLeagueTableCommand;
import com.capgemini.scores.gateway.message.MatchResultCommand;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({CommandGatewayApplication.class})
@WebIntegrationTest({"kafka.addresses=localhost:" + BaseKafkaTest.BROKER_PORT,
        "zookeeper.address=localhost:" + BaseKafkaTest.ZOOKEEPER_PORT})
public class RestMatchResultTest extends BaseKafkaTest {

    @Test
    public void testMatchResult() throws TimeoutException {
        final String uri = "http://localhost:8080/league/testLeague/result";

        final MatchResult result = new MatchResult(null, "Tottenham Hotspur", 4, "Arsenal", 1);
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, result);

        final List<String> messages = readMessages(Topics.MATCH_RESULT_COMMAND, 1);

        final MatchResultCommand publishedCommand =
                new Gson().fromJson(messages.get(0), MatchResultCommand.class);

        final MatchResult publishedResult = publishedCommand.getPayload();

        assertEquals("Published match result competition id incorrect", "testLeague", publishedResult.getCompetitionId());
        assertEquals("Home team in result incorrect", "Tottenham Hotspur", publishedResult.getHomeTeam());
        assertEquals("Away team in result incorrect", "Arsenal", publishedResult.getAwayTeam());
        assertEquals("Home team score in result incorrect", 4, publishedResult.getHomeScore());
        assertEquals("Away team score in result incorrect", 1, publishedResult.getAwayScore());

    }
}
