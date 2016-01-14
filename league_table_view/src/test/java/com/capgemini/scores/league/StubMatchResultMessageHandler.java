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

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import com.capgemini.scores.league.handler.TopicMessageHandler;

/**
 * A stub message handler for matchResult messages.
 * 
 * Allows the ability to be able to check what messages have been handled.
 * 
 * @author craigwilliams84
 *
 */
public class StubMatchResultMessageHandler implements TopicMessageHandler{
    
    private List<Message<?>> handledMessages = new ArrayList<Message<?>>();
    
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        handledMessages.add(message);
        
    }
    
    public List<Message<?>> getHandledMessages() {
        return handledMessages;
    }

    @Override
    public String getTopic() {
        return Topics.MATCH_RESULT;
    }

}
