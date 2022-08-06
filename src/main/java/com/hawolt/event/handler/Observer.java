package com.hawolt.event.handler;

/**
 * Created: 17/04/2022 23:58
 * Author: Twitter @hawolt
 **/

public interface Observer<T> {
    void addObserver(T t);
}
