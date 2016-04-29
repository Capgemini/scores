package com.capgemini.scores.league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.capgemini.gregor.EnableKafkaConsumers;
import com.capgemini.gregor.EnableKafkaProducers;

/**
 * A spring boot based event-sourcing aggregate microservice for League Tables.
 * 
 * @author craigwilliams84
 *
 */
@SpringBootApplication
//@Import({MongoConfiguration.class})
@EnableKafkaConsumers
@EnableKafkaProducers
public class LeagueTableAggregateApplication {
    
    public static void main(String[] args) {      
        SpringApplication.run(LeagueTableAggregateApplication.class, args);
    }  
}
