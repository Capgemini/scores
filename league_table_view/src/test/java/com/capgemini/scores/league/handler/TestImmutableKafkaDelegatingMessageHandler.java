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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;

public class TestImmutableKafkaDelegatingMessageHandler {
    
    private static final String TOPIC = "topic1";
    
    private static final String ANOTHER_TOPIC = "topic2";
    
    @Test
    public void testHandleMessageWithSingleHandlerAndCorrectTopic() {
        final StubHandler handler = createStubHandler(TOPIC);
        
        final ImmutableKafkaDelegatingMessageHandler handlerToTest = createHandlerUnderTest(handler);
        
        final Message<?> message = createMessage(TOPIC);
        
        handlerToTest.handleMessage(message);
        
        assertMessagesHandled(handler, message);
    }
    
    @Test
    public void testHandleMessageWithMultipleHandlersAndCorrectTopic() {
        final StubHandler handler1 = createStubHandler(TOPIC);
        final StubHandler handler2 = createStubHandler(ANOTHER_TOPIC);
        
        final ImmutableKafkaDelegatingMessageHandler handlerToTest = createHandlerUnderTest(handler1, handler2);
        
        final Message<?> message = createMessage(ANOTHER_TOPIC);
        
        handlerToTest.handleMessage(message);
        
        assertMessagesHandled(handler2, message);
        assertMessagesNotHandled(handler1, message);
    }
    
    @Test
    public void testHandleMessageWithNotHandledTopic() {
        final StubHandler handler = createStubHandler(TOPIC);
        
        final ImmutableKafkaDelegatingMessageHandler handlerToTest = createHandlerUnderTest(handler);
        
        final Message<?> message = createMessage(ANOTHER_TOPIC);
        
        handlerToTest.handleMessage(message);
        
        assertMessagesNotHandled(handler, message);
    }
    
    @Test
    public void testGetTopics() {
        final StubHandler handler1 = createStubHandler(TOPIC);
        final StubHandler handler2 = createStubHandler(ANOTHER_TOPIC);
        
        final ImmutableKafkaDelegatingMessageHandler handlerToTest = createHandlerUnderTest(handler1, handler2);
        
        final Set<String> topics = handlerToTest.getTopics();
        
        assertEquals("Incorrect number of topics", 2, topics.size());
        assertEquals(TOPIC + " not within topics", true, topics.contains(TOPIC));
        assertEquals(ANOTHER_TOPIC + " not within topics", true, topics.contains(ANOTHER_TOPIC));
    }
    
    @Test(expected=IllegalStateException.class)
    public void testContructWithHandlersWithDuplicateTopics() {
        final StubHandler handler1 = createStubHandler(TOPIC);
        final StubHandler handler2 = createStubHandler(TOPIC);
        
        createHandlerUnderTest(handler1, handler2);
    }
    
    private void assertMessagesHandled(StubHandler handler, Message<?>... messages) {
        final List<Message<?>> messagesHandled = handler.getHandledMessages();
        
        assertEquals("Incorrect number of messages handled", messages.length, messagesHandled.size());
        
        for (Message<?> message : messages) {
            assertEquals("Not all messages handled", true, messagesHandled.contains(message));
        }
    }
    
    private void assertMessagesNotHandled(StubHandler handler, Message<?>... messages) {
        final List<Message<?>> messagesHandled = handler.getHandledMessages();
        
        for (Message<?> message : messages) {
            assertEquals("Message handled when it should not have been!", false, messagesHandled.contains(message));
        }
    }
    
    private ImmutableKafkaDelegatingMessageHandler createHandlerUnderTest(TopicMessageHandler... handlers) {
        return new ImmutableKafkaDelegatingMessageHandler(handlers);
    }
    
    private StubHandler createStubHandler(String topic) {
        return new StubHandler(topic);
    }
    
    private Message<String> createMessage(String topic) {
        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put(KafkaHeaders.TOPIC, topic);
        
        final Message<String> message = new GenericMessage<String>("This is a payload", headers);
        
        return message;
    }
    
    private class StubHandler implements TopicMessageHandler{
        
        private String topic;
        
        private List<Message<?>> handledMessages = new ArrayList<Message<?>>();
        
        private StubHandler(String topic) {
            this.topic = topic;
        }

        @Override
        public void handleMessage(Message<?> message) throws MessagingException {
            handledMessages.add(message);
        }

        @Override
        public String getTopic() {
            return topic;
        }
        
        public List<Message<?>> getHandledMessages() {
            return handledMessages;
        }
        
    }
}
