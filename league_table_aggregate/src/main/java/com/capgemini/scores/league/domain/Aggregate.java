package com.capgemini.scores.league.domain;

import java.util.List;

import com.capgemini.scores.league.message.Command;
import com.capgemini.scores.league.message.Event;

public interface Aggregate {
    public List<Event> process(Command command);
}
