package com.capgemini.scores.league.aggregate.exception;

public class TeamNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 4509801128879267468L;

    private static final String MESSAGE = "Team name, %s, not associated with league, %s";
    
    public TeamNotFoundException(String teamName, String leagueName) {
        super(String.format(MESSAGE, teamName, leagueName));
    }
}
