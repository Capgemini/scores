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

package com.capgemini.scores.gateway.integration;

import com.capgemini.gregor.KafkaClient;
import com.capgemini.gregor.KafkaProducer;
import com.capgemini.gregor.serializer.JSONSerializer;
import com.capgemini.scores.Topics;
import com.capgemini.scores.gateway.message.CreateLeagueTableCommand;
import com.capgemini.scores.gateway.message.MatchResultCommand;

/**
 * Publishes commands to the wider system
 */
@KafkaClient
public interface CommandPublisher {

    @KafkaProducer(topic = Topics.CREATE_LEAGUE_TABLE_COMMAND, payloadSerializer = JSONSerializer.class)
    void publishCreateLeagueTableCommand(CreateLeagueTableCommand command);

    @KafkaProducer(topic = Topics.MATCH_RESULT_COMMAND, payloadSerializer = JSONSerializer.class)
    void publishMatchResultCommand(MatchResultCommand command);
}
