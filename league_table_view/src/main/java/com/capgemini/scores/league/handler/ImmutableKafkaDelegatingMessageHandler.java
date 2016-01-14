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

package com.capgemini.scores.league.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * A message handler that can handle kafka messages from multiple explicit topics.
 * 
 * Handling is delegated to sub-handlers depending on the topic of the message.
 * 
 * Handlers are registered during construction and cannot be modified afterwards
 * 
 * @author craigwilliams84
 *
 */
@Component
public class ImmutableKafkaDelegatingMessageHandler implements MultiTopicMessageHandler {

    private static final Logger LOG = Logger.getLogger(ImmutableKafkaDelegatingMessageHandler.class);
    
    private Map<String, MessageHandler> handlers = new HashMap<String, MessageHandler>();
    
    @Autowired
    public ImmutableKafkaDelegatingMessageHandler(TopicMessageHandler[] handlers) {
        for (TopicMessageHandler handler : handlers) {
            registerHandler(handler);
        }
    }
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        final String topic = extractTopic(message);
        final MessageHandler handler = getMessageHandler(topic);
                
        if (handler != null) {
            handler.handleMessage(message);
        } else {
            LOG.warn("No handler registered for topic: " + topic);
        }
    }
    
    @Override
    public Set<String> getTopics() {
        return handlers.keySet();
    }
    
    private String extractTopic(Message<?> message) {
        return message.getHeaders().get(KafkaHeaders.TOPIC, String.class);
    }
    
    private MessageHandler getMessageHandler(String topic) {
        return handlers.get(topic);
    }
    
    private void registerHandler(TopicMessageHandler handler) {
        if (handlers.containsKey(handler.getTopic())) {
            throw new IllegalStateException("A handler for topic " + handler.getTopic() 
                    + " already exists.  Multiple handlers per topic is not currently supported.");
        } else {
            handlers.put(handler.getTopic(), handler);
        }
    }

}
