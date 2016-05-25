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

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Configures mongodb so that customer converters can be used.
 * 
 * (To add a custom converter, simply create the converter bean).
 * 
 * @author craigwilliams84
 *
 */
@Configuration
public class MongoConfiguration {
    
    @Bean
    public CustomConversions customConversions(Converter<?,?>[] converters) {
        final List<Converter<?,?>> converterList = Arrays.asList(converters);
        
        return new CustomConversions(converterList);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory mongoDbFactory, 
            MongoMappingContext mongoMappingContext, CustomConversions customConversions) throws Exception {

        final MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), mongoMappingContext);
        converter.setCustomConversions(customConversions);

        return converter;
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MappingMongoConverter mappingMongoConverter) throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory, mappingMongoConverter);
    }
}
