package com.capgemini.scores.league.aggregate.message;

import com.capgemini.scores.league.aggregate.domain.MatchResult;
import com.capgemini.scores.message.Event;

public class MatchResultEvent implements Event<MatchResult> {

    private MatchResult matchResult;
    
    public MatchResultEvent(MatchResult matchResult) {
        this.matchResult = matchResult;
    }
    
    @Override
    public MatchResult getPayload() {
        return matchResult;
    }

}
