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

package com.capgemini.scores.league;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the receiving of kafka messages.
 * 
 * @author craigwilliams84
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({LeagueTableView.class, EndToEndTestConfiguration.class})
@IntegrationTest({"kafka.address=localhost:" + BaseKafkaTest.BROKER_PORT, 
    "zookeeper.address=localhost:" + BaseKafkaTest.ZOOKEEPER_PORT})
@ActiveProfiles("endToEndTest")
public class TestEndToEndReceivingMessages extends BaseKafkaTest {

    private static final String MESSAGE_VALUE = "Testing123";
    
    private static final String ANOTHER_MESSAGE_VALUE = "Testing Testing 123";
    
    @Autowired
    StubMatchResultMessageHandler stubHandler;
    
    @Test
    public void testMessagesAreReceivedByHandler() throws InterruptedException {
        //Send a message
        sendMessage(stubHandler.getTopic(), MESSAGE_VALUE);
        
        //Wait a second for the message to be delivered
        Thread.sleep(1000);
        
        assertEquals("Handler hasn't received first message", 1, stubHandler.getHandledMessages().size());
        assertEquals("Message payload is not correct", new String(MESSAGE_VALUE), stubHandler.getHandledMessages().get(0).getPayload());
        
        //Send another one
        sendMessage(stubHandler.getTopic(), ANOTHER_MESSAGE_VALUE);
        
        //Wait a second for the message to be delivered
        Thread.sleep(1000);
        
        assertEquals("Handler hasn't received second message", 2, stubHandler.getHandledMessages().size());
        assertEquals("Message payload is not correct", new String(ANOTHER_MESSAGE_VALUE), stubHandler.getHandledMessages().get(1).getPayload());
    }
}
