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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.capgemini.scores.league.LeagueTableUpdater;
import com.capgemini.scores.league.Topics;
import com.capgemini.scores.league.domain.MatchResult;

/**
 * A message handler that handles messages sent to the matchResult topic.
 * 
 * @author craigwilliams84
 *
 */
//TODO Decoding should be handled at a lower level, not here, but will wait until the kafka
//frameworky stuff is hardened before moving.
@Component
@Profile("default")
public class MatchResultMessageHandler implements TopicMessageHandler {

    private static final Logger LOG = Logger.getLogger(MatchResultMessageHandler.class);
    
    private MatchResultJSONPayloadDecoder decoder;
    
    private LeagueTableUpdater updater;
    
    @Autowired
    public MatchResultMessageHandler(MatchResultJSONPayloadDecoder decoder, LeagueTableUpdater updater) {
        this.decoder = decoder;
        this.updater = updater;
    }
    
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        LOG.info("Message received for topic " + Topics.MATCH_RESULT + ": " + message);
        
        final MatchResult result = decoder.fromBytes(((String)message.getPayload()).getBytes());
        
        updateLeagueTable(result);
    }

    @Override
    public String getTopic() {
        return Topics.MATCH_RESULT;
    }

    private void updateLeagueTable(MatchResult result) {
        updater.updateTable(result);
    }
}
