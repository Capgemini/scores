package com.capgemini.scores.gateway.message;

import com.capgemini.scores.gateway.domain.MatchResult;
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
