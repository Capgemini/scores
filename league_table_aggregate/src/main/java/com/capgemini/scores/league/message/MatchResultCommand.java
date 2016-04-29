package com.capgemini.scores.league.message;

import com.capgemini.scores.league.domain.MatchResult;

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
