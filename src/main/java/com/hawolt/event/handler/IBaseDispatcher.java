package com.hawolt.event.handler;

/**
 * Created: 16/04/2022 14:36
 * Author: Twitter @hawolt
 **/

public interface IBaseDispatcher<T, S> {
    void dispatch(T t, S s);
}
