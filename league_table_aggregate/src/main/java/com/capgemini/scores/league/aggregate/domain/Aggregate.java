package com.capgemini.scores.league.aggregate.domain;

import java.util.List;

import com.capgemini.scores.league.aggregate.message.Command;
import com.capgemini.scores.league.aggregate.message.Event;

public interface Aggregate {
    public List<Event> process(Command command);
}
