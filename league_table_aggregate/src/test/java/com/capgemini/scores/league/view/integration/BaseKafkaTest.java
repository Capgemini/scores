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

package com.capgemini.scores.league.view.integration;

import com.capgemini.scores.Topics;
import info.batey.kafka.unit.KafkaUnit;
import kafka.producer.KeyedMessage;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Base class for tests that require a kafka instance to be running.
 * 
 * Provides helper, kafka related methods.
 * 
 * @author craigwilliams84
 *
 */
public class BaseKafkaTest {

    private static final Long DEFAULT_WAIT_TIME = 2000l;
    
    public static final int BROKER_PORT = 9093;
    
    public static final int ZOOKEEPER_PORT = 2183;
    
    private static KafkaUnit kafka;
    
    @BeforeClass
    public static void startKafka() {
        kafka = new KafkaUnit(ZOOKEEPER_PORT, BROKER_PORT);
        kafka.startup();

        sleep(DEFAULT_WAIT_TIME);

        kafka.createTopic(Topics.MATCH_RESULT_COMMAND);
        kafka.createTopic(Topics.CREATE_LEAGUE_TABLE_COMMAND);
        kafka.createTopic(Topics.MATCH_RESULT_EVENT);
        kafka.createTopic(Topics.CREATE_LEAGUE_TABLE_EVENT);
    }
    
    @AfterClass
    public static void shutdownKafka() {
        kafka.shutdown();
        sleep(DEFAULT_WAIT_TIME);
    }
    
    protected void createTopic(String topicName) {
        kafka.createTopic(topicName);
    }
    
    protected void sendMessage(String topic, String value) {
        final KeyedMessage<String, String> message = new KeyedMessage<String, String>(topic, value);
        
        kafka.sendMessages(message);
    }

    protected List<String> readMessages(String topicName, int expectedMessages) throws TimeoutException {
        return kafka.readMessages(topicName, expectedMessages);
    }

    protected void pause() {
        sleep(DEFAULT_WAIT_TIME);
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
