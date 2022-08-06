package com.hawolt.event.handler.socket;

import com.hawolt.event.handler.AbstractObserver;

/**
 * Created: 20/04/2022 11:29
 * Author: Twitter @hawolt
 **/

public class SocketHandler extends AbstractObserver<SocketIssue, ISocketListener> {

    @Override
    public void dispatch(SocketIssue socketIssue) {
        for (ISocketListener listener : observers) {
            listener.onConnectionIssue();
        }
    }
}