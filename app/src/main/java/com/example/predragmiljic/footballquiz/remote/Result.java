package com.example.predragmiljic.footballquiz.remote;

public interface Result<T> {
    void onSuccess(T result);
    void onFailure(String error);
}
