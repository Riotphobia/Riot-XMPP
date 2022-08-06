package com.hawolt.event.handler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created: 30/04/2022 16:40
 * Author: Twitter @hawolt
 **/

public abstract class AbstractObserver<T, S> implements Observer<S>, ISimpleDispatcher<T> {

    protected final List<S> observers = new LinkedList<>();

    public void addObserver(S s) {
        this.observers.add(s);
    }
}
