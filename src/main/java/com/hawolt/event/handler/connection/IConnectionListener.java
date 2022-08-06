package com.hawolt.event.handler.connection;

/**
 * Created: 17/04/2022 23:55
 * Author: Twitter @hawolt
 **/

public interface IConnectionListener {
    void onOffline(String jid);

    void onMobile(String jid);

    void onOnline(String jid);
}
