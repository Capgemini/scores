package com.capgemini.scores.league.aggregate.message;

import com.capgemini.scores.league.aggregate.domain.MatchResult;
import com.capgemini.scores.message.Command;

public class MatchResultCommand implements Command<MatchResult> {

    private MatchResult matchResult;
    
    public MatchResultCommand(MatchResult matchResult) {
        this.matchResult = matchResult;
    }
    
    @Override
    public MatchResult getPayload() {
        return matchResult;
    }

}
