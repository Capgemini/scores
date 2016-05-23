package com.capgemini.scores.league.integration;

import com.capgemini.scores.league.aggregate.LeagueTableAggregateApplication;
import com.capgemini.scores.Topics;
import com.capgemini.scores.league.aggregate.domain.LeagueTable;
import com.capgemini.scores.league.aggregate.message.LeagueTableCreatedEvent;
import com.capgemini.scores.league.aggregate.repository.LeagueTableRepository;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({LeagueTableAggregateApplication.class})
@IntegrationTest({"kafka.addresses=localhost:" + BaseKafkaTest.BROKER_PORT,
        "zookeeper.address=localhost:" + BaseKafkaTest.ZOOKEEPER_PORT})
public class TestProcessingCreateLeagueTableCommands extends BaseKafkaTest {

    @Autowired
    private LeagueTableRepository repository;

    @Test
    @DirtiesContext
    public void testProcessCreateLeagueTableCommand() throws TimeoutException {
        final String createLeagueTableCommandJson = "{\"leagueTable\"={\"id\":\"Test League\",\"teams\":[\"Arsenal\",\"Tottenham\"],\"results\":[]}}";

        sendMessage(Topics.CREATE_LEAGUE_TABLE_COMMAND, createLeagueTableCommandJson);

        pause();

        final LeagueTable leagueTable = repository.getLeagueTable("Test League");
        assertNotNull("League table has not been saved", leagueTable);
        assertEquals("League table does not have the correct number of teams", 2, leagueTable.getTeams().size());

        final List<String> teams = new ArrayList<String>(leagueTable.getTeams());

        assertEquals("First team name incorrect", "Arsenal", teams.get(0));
        assertEquals("Second team name incorrect", "Tottenham", teams.get(1));

        assertEquals("League in published event incorrect", leagueTable, getPublishedLeagueTableCreatedEvent().getPayload());
    }

    private LeagueTableCreatedEvent getPublishedLeagueTableCreatedEvent() throws TimeoutException {
        final List<String> messages = readMessages(Topics.CREATE_LEAGUE_TABLE_EVENT, 1);

        assertEquals("Incorrect number of events sent", 1, messages.size());

        final String message = messages.get(0);

        final Gson gson = new Gson();
        return gson.fromJson(message, LeagueTableCreatedEvent.class);
    }
}
