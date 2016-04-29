package com.capgemini.scores.league.message;

public interface PayloadMessage<T>  {
    T getPayload();
}
