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

package com.capgemini.scores.league.view.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * League table domain object.
 * 
 * @author craigwilliams84
 *
 */
@Document
public class LeagueTable {
    
    @Id
    private String name;

    private Long version;
    
    private List<LeagueTableEntry> entries;
    
    public LeagueTable(String name, List<LeagueTableEntry> entries) {
        this.name = name;
        this.entries = entries;

        version = new Long(0l);
    }
    
    public String getName() {
        return name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    public List<LeagueTableEntry> getEntries() {
        return entries;
    }
    
    public LeagueTableEntry findEntry(String teamName) {
        for (LeagueTableEntry entry : entries) {
            if (entry.getTeamName().equals(teamName)) {
                return entry;
            }
        }
        
        return null;
    }
}
