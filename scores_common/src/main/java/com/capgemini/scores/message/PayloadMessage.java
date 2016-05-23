package com.capgemini.scores.message;

public interface PayloadMessage<T>  {
    T getPayload();
}
