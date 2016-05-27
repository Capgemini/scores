package com.capgemini.scores.league.view.integration;

import com.capgemini.scores.league.aggregate.LeagueTableAggregateApplication;
import com.capgemini.scores.Topics;
import com.capgemini.scores.league.aggregate.domain.LeagueTable;
import com.capgemini.scores.league.aggregate.domain.MatchResult;
import com.capgemini.scores.league.aggregate.message.MatchResultEvent;
import com.capgemini.scores.league.aggregate.repository.LeagueTableRepository;
import com.google.gson.Gson;
import org.junit.After;
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

    private static final String LEAGUE_ID = "Test League1";

    private static String MATCH_RESULT_COMMAND_JSON = "{\"matchResult\":{\"competitionId\":\"Test League1\",\"homeTeam\":\"Chelsea\",\"homeScore\":1,\"awayTeam\":\"Tottenham\",\"awayScore\":5}}";

    @Autowired
    private LeagueTableRepository repository;

    @After
    public void cleanup() {
        repository.delete(repository.getLeagueTable(LEAGUE_ID));
    }

    @Test
    public void testProcessMatchResultCommand() throws TimeoutException {
        final LeagueTable table = new LeagueTable();

        table.setId(LEAGUE_ID);

        final Set<String> teams = new HashSet<String>();
        teams.add("Tottenham");
        teams.add("Chelsea");

        table.setTeams(teams);

        repository.save(table);

        System.out.println("***** Sending match result");
        sendMessage(Topics.MATCH_RESULT_COMMAND, MATCH_RESULT_COMMAND_JSON);

        pause();

        final LeagueTable modifiedLeagueTable = repository.getLeagueTable(LEAGUE_ID);
        assertEquals("League table does not have a result associated with it", 1, modifiedLeagueTable.getResults().size());
        assertEquals("League table does not have the correct version", new Long(1l), modifiedLeagueTable.getVersion());

        final MatchResult matchResult = modifiedLeagueTable.getResults().iterator().next();

        assertEquals("Result competition name incorrect", LEAGUE_ID, matchResult.getCompetitionId());
        assertEquals("Result home team incorrect", "Chelsea", matchResult.getHomeTeam());
        assertEquals("Result home score incorrect", 1, matchResult.getHomeScore());
        assertEquals("Result away team incorrect", "Tottenham", matchResult.getAwayTeam());
        assertEquals("Result away score incorrect", 5, matchResult.getAwayScore());

        final MatchResultEvent matchResultEvent = getPublishedMatchResultEvent();

        assertEquals("Match result in event is incorrect", matchResult, matchResultEvent.getPayload());
    }

    @Test
    public void testVersionOfLeagueTableIsIncrementedAfterMatchResultCommands() throws TimeoutException {


        final LeagueTable table = new LeagueTable();

        table.setId(LEAGUE_ID);

        final Set<String> teams = new HashSet<String>();
        teams.add("Tottenham");
        teams.add("Chelsea");

        table.setTeams(teams);

        repository.save(table);

        sendMessage(Topics.MATCH_RESULT_COMMAND, MATCH_RESULT_COMMAND_JSON);

        pause();

        LeagueTable modifiedLeagueTable = repository.getLeagueTable(LEAGUE_ID);
        assertEquals("League table does not have the correct version", new Long(1l), modifiedLeagueTable.getVersion());

        sendMessage(Topics.MATCH_RESULT_COMMAND, MATCH_RESULT_COMMAND_JSON);

        pause();

        modifiedLeagueTable = repository.getLeagueTable(LEAGUE_ID);
        assertEquals("League table does not have the correct version", new Long(2l), modifiedLeagueTable.getVersion());

        sendMessage(Topics.MATCH_RESULT_COMMAND, MATCH_RESULT_COMMAND_JSON);

        pause();

        modifiedLeagueTable = repository.getLeagueTable(LEAGUE_ID);
        assertEquals("League table does not have the correct version", new Long(3l), modifiedLeagueTable.getVersion());

    }

    private MatchResultEvent getPublishedMatchResultEvent() throws TimeoutException {
        final List<String> messages = readMessages(Topics.MATCH_RESULT_EVENT, 1);

        assertEquals("Incorrect number of events sent", 1, messages.size());

        final String message = messages.get(0);

        final Gson gson = new Gson();
        return gson.fromJson(message, MatchResultEvent.class);
    }
}
