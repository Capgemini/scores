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

package com.capgemini.scores.league.integration;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.scores.league.BaseKafkaTest;
import com.capgemini.scores.league.LeagueTableView;
import com.capgemini.scores.league.MongoTestConfiguration;
import com.capgemini.scores.league.Topics;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({LeagueTableView.class})
@IntegrationTest({"kafka.address=localhost:" + BaseKafkaTest.BROKER_PORT, 
    "zookeeper.address=localhost:" + BaseKafkaTest.ZOOKEEPER_PORT})
@Import(MongoTestConfiguration.class)
public class TestProcessingMatchResults extends BaseKafkaTest {
    
    private static final String MESSAGE_VALUE = "{\"competitionId\": \"Test League\",\"homeTeam\": \"Tottenham Hotspur\",\"homeScore\": 5,\"awayTeam\": \"Arsenal\",\"awayScore\": 1}";
    
    //Required by nosql-unit
    @Autowired 
    private ApplicationContext applicationContext;
    
    @Autowired
    private Mongo fongo;
    
    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("leagues");
    
    @Test
    @UsingDataSet(locations = {"/base_test_league.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    @ShouldMatchDataSet(location = "/league_spurs_win.json")
    public void testMessagesAreReceivedByHandler() throws InterruptedException {
        //Send a message
        sendMessage(Topics.MATCH_RESULT, MESSAGE_VALUE);
        
        //Wait a few seconds for the message to be delivered
        Thread.sleep(3000);
    }
}
