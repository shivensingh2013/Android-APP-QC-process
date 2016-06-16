package com.app.overboxsample.network.interfaces;

public interface ITaskListener<T> {
    void taskResponseReceived(T obj, int taskId);
}
