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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.scores.league.domain.LeagueTable;
import com.capgemini.scores.league.domain.repository.LeagueTableRepository;

/**
 * A spring boot based event-sourcing microservice that will eventually consume match result events
 * and subsequently modify its league table database.
 * 
 * @author craigwilliams84
 *
 */
@SpringBootApplication
@Import({MongoConfiguration.class, KafkaConsumerConfiguration.class})
public class LeagueTableView {

    public static void main(String[] args) {      
        SpringApplication.run(LeagueTableView.class, args);
    }  
}
