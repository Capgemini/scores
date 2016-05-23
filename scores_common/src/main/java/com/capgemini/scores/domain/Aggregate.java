package com.capgemini.scores.domain;

import com.capgemini.scores.message.Command;
import com.capgemini.scores.message.Event;

import java.util.List;

public interface Aggregate {
    public List<Event> process(Command command);
}
