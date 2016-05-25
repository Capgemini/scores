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

package com.capgemini.scores.league.view;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.capgemini.scores.league.view.domain.LeagueTable;
import com.capgemini.scores.league.view.domain.LeagueTableEntry;
import com.capgemini.scores.league.view.domain.LeagueTeamStatistics;
import com.capgemini.scores.league.view.domain.repository.LeagueTableRepository;

/**
 * Creates dummy league data if the capgemini.createdummydata property is set to true
 * 
 * The dummy league has the name "DummyLeague", and contains 2 teams,
 * "Tottenham Hotspur" and "Arsenal".
 * 
 * @author craigwilliams84
 *
 */
@Configuration
@ConditionalOnProperty(name = "capgemini.createdummydata", havingValue = "true")
public class DummyLeagueDataConfiguration {

    @Bean
    public BeanPostProcessor dummyDataProcessor() {
        return new DummyLeagueDataCreator();
    }
    
    private class DummyLeagueDataCreator implements BeanPostProcessor {

        private static final String LEAGUE_NAME = "DummyLeague";
        
        private static final String TEAM_ONE = "Tottenham Hotspur";
        
        private static final String TEAM_TWO = "Arsenal";
        
        @Override
        public Object postProcessBeforeInitialization(Object bean,
                String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean,
                String beanName) throws BeansException {
            
            if (bean instanceof LeagueTableRepository) {
                //Insert dummy, 2 team league
                final LeagueTableRepository repository = (LeagueTableRepository) bean;
            
                final LeagueTableEntry entry1 = new LeagueTableEntry(TEAM_ONE, 0, new LeagueTeamStatistics());
                final LeagueTableEntry entry2 = new LeagueTableEntry(TEAM_TWO, 0, new LeagueTeamStatistics());
                final LeagueTable table = new LeagueTable(LEAGUE_NAME, Arrays.asList(entry1, entry2));

                if (!repository.exists(table.getName())) {
                    repository.insert(table);
                }
            }
            
            return bean;
        } 
    }
}
