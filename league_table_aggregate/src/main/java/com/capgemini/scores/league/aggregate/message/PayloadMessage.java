package com.capgemini.scores.league.aggregate.message;

public interface PayloadMessage<T>  {
    T getPayload();
}
