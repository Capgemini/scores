package com.capgemini.scores.league.view.integration;

import com.capgemini.scores.league.aggregate.LeagueTableAggregateApplication;
import com.capgemini.scores.Topics;
import com.capgemini.scores.league.aggregate.domain.LeagueTable;
import com.capgemini.scores.league.aggregate.domain.MatchResult;
import com.capgemini.scores.league.aggregate.message.MatchResultEvent;
import com.capgemini.scores.league.aggregate.repository.LeagueTableRepository;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({LeagueTableAggregateApplication.class})
@IntegrationTest({"kafka.addresses=localhost:" + BaseKafkaTest.BROKER_PORT,
        "zookeeper.address=localhost:" + BaseKafkaTest.ZOOKEEPER_PORT})
public class TestProcessingMatchResultCommands extends BaseKafkaTest {

    @Autowired
    private LeagueTableRepository repository;

    @Test
    @DirtiesContext
    public void testProcessMatchResultCommand() throws TimeoutException {
        final String matchResultCommandJson = "{\"matchResult\":{\"competitionId\":\"Test League1\",\"homeTeam\":\"Arsenal\",\"homeScore\":1,\"awayTeam\":\"Tottenham\",\"awayScore\":5}}";

        final LeagueTable table = new LeagueTable();

        table.setId("Test League1");

        final Set<String> teams = new HashSet<String>();
        teams.add("Tottenham");
        teams.add("Arsenal");

        table.setTeams(teams);

        repository.save(table);

        System.out.println("***** Sending match result");
        sendMessage(Topics.MATCH_RESULT_COMMAND, matchResultCommandJson);

        pause();

        final LeagueTable modifiedLeagueTable = repository.getLeagueTable("Test League1");
        assertEquals("League table does not have a result associated with it", 1, modifiedLeagueTable.getResults().size());

        final MatchResult matchResult = modifiedLeagueTable.getResults().iterator().next();

        assertEquals("Result competition name incorrect", "Test League1", matchResult.getCompetitionId());
        assertEquals("Result home team incorrect", "Arsenal", matchResult.getHomeTeam());
        assertEquals("Result home score incorrect", 1, matchResult.getHomeScore());
        assertEquals("Result away team incorrect", "Tottenham", matchResult.getAwayTeam());
        assertEquals("Result away score incorrect", 5, matchResult.getAwayScore());

        final MatchResultEvent matchResultEvent = getPublishedMatchResultEvent();

        assertEquals("Match result in event is incorrect", matchResult, matchResultEvent.getPayload());
    }

    private MatchResultEvent getPublishedMatchResultEvent() throws TimeoutException {
        final List<String> messages = readMessages(Topics.MATCH_RESULT_EVENT, 1);

        assertEquals("Incorrect number of events sent", 1, messages.size());

        final String message = messages.get(0);

        final Gson gson = new Gson();
        return gson.fromJson(message, MatchResultEvent.class);
    }
}
