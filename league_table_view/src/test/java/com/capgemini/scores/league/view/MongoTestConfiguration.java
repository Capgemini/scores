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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories
public class MongoTestConfiguration extends AbstractMongoConfiguration {

    @Autowired
    private Converter<?,?>[] converters;
    
    @Override
    protected String getDatabaseName() {
        return "leagues";
    }

    @Override
    @Bean
    public Mongo mongo() {
        return new Fongo("leagues").getMongo();
    }
    
    @Override
    @Bean
    public CustomConversions customConversions() {
        final List<Converter<?,?>> converterList = Arrays.asList(converters);
        
        return new CustomConversions(converterList);
    }
}
