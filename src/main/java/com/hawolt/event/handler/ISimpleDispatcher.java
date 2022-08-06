package com.hawolt.event.handler;

/**
 * Created: 16/04/2022 14:36
 * Author: Twitter @hawolt
 **/

public interface ISimpleDispatcher<T> {
    void dispatch(T t);
}
