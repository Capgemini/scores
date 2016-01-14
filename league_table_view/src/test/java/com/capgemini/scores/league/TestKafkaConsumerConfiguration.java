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

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LeagueTableView.class)
@IntegrationTest({"kafka.address=localhost:" + BaseKafkaTest.BROKER_PORT, 
    "zookeeper.address=localhost:" + BaseKafkaTest.ZOOKEEPER_PORT})
public class TestKafkaConsumerConfiguration extends BaseKafkaTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testContainerBeanIsRegistered() {
        try {
        
            applicationContext.getBean("kafkaContainer", KafkaMessageListenerContainer.class);
    
        } catch (Throwable t) {
            t.printStackTrace();
            TestCase.fail("The kafka container has not been registered correctly");
        }
    }
    
    @Test
    public void testAdapterBeanIsRegistered() {
        try {
            applicationContext.getBean("kafkaAdapter", KafkaMessageDrivenChannelAdapter.class);
        } catch (Throwable t) {
            t.printStackTrace();
            TestCase.fail("The kafka adapter has not been registered correctly");
        }
    }
}
