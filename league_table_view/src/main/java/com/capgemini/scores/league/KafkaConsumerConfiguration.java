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

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.kafka.core.BrokerAddress;
import org.springframework.integration.kafka.core.BrokerAddressListConfiguration;
import org.springframework.integration.kafka.core.ConnectionFactory;
import org.springframework.integration.kafka.core.DefaultConnectionFactory;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.integration.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.integration.kafka.serializer.common.StringDecoder;
import org.springframework.messaging.MessageChannel;

import com.capgemini.scores.league.handler.MultiTopicMessageHandler;

/**
 * Configures the beans required in order to consume messages from a Kafka instance.
 * 
 * @author craigwilliams84
 *
 */
@Configuration
public class KafkaConsumerConfiguration {
    
    @Autowired
    private KafkaConfiguration config;
    
    @Autowired
    private MultiTopicMessageHandler delegatingMessageHandler;
    
    @Bean
    public ConnectionFactory kafkaBrokerConnectionFactory() throws Exception {
        return new DefaultConnectionFactory(brokerConfiguration());
    }

    @Bean
    public org.springframework.integration.kafka.core.Configuration brokerConfiguration() {
        BrokerAddressListConfiguration configuration = new BrokerAddressListConfiguration(
                BrokerAddress.fromAddress(config.getBrokerAddress()));
        configuration.setSocketTimeout(500);
        return configuration;
    }
    
    @Bean
    public KafkaMessageListenerContainer container() throws Exception {
        final Set<String> supportedTopics = delegatingMessageHandler.getTopics();
        final KafkaMessageListenerContainer kafkaMessageListenerContainer = new KafkaMessageListenerContainer(
                kafkaBrokerConnectionFactory(), supportedTopics.toArray(new String[supportedTopics.size()]));
        kafkaMessageListenerContainer.setMaxFetch(100);
        kafkaMessageListenerContainer.setConcurrency(1);
        return kafkaMessageListenerContainer;
    }

    @Bean
    public KafkaMessageDrivenChannelAdapter adapter(KafkaMessageListenerContainer container) {
        KafkaMessageDrivenChannelAdapter kafkaMessageDrivenChannelAdapter =
                new KafkaMessageDrivenChannelAdapter(container);
        StringDecoder decoder = new StringDecoder();
        kafkaMessageDrivenChannelAdapter.setKeyDecoder(decoder);
        kafkaMessageDrivenChannelAdapter.setPayloadDecoder(decoder);
        kafkaMessageDrivenChannelAdapter.setOutputChannel(received());
        return kafkaMessageDrivenChannelAdapter;
    }
    
    @Bean
    public MessageChannel received() {
        final PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.subscribe(delegatingMessageHandler);
        
        return channel;
    }
}
