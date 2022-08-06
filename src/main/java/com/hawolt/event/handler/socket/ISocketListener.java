package com.hawolt.event.handler.socket;

/**
 * Created: 20/04/2022 11:23
 * Author: Twitter @hawolt
 **/

public interface ISocketListener {

    String getConnectionIdentifier();

    void onConnectionIssue();

    void onSessionExpired();

    void onTermination();
}
