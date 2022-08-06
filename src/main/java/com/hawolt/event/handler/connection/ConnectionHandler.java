package com.hawolt.event.handler.connection;

import com.hawolt.event.handler.AbstractObserver;
import com.hawolt.event.objects.presence.AbstractPresence;
import com.hawolt.event.objects.presence.ConnectionStatus;
import com.hawolt.event.objects.presence.impl.MobilePresence;
import com.hawolt.event.objects.presence.impl.OfflinePresence;

import java.util.HashMap;
import java.util.Map;

/**
 * Created: 30/04/2022 16:43
 * Author: Twitter @hawolt
 **/

public class ConnectionHandler extends AbstractObserver<AbstractPresence, IConnectionListener> {
    private final Map<String, ConnectionStatus> cache = new HashMap<>();

    @Override
    public void dispatch(AbstractPresence presence) {
        String jid = presence.getFrom();
        ConnectionStatus status = presence instanceof OfflinePresence ? ConnectionStatus.OFFLINE : presence instanceof MobilePresence ? ConnectionStatus.MOBILE : ConnectionStatus.ONLINE;
        if (cache.getOrDefault(jid, ConnectionStatus.UNKNOWN) == status) return;
        for (IConnectionListener listener : observers) {
            switch (status) {
                case OFFLINE:
                    listener.onOffline(jid);
                    break;
                case ONLINE:
                    listener.onOnline(jid);
                    break;
                case MOBILE:
                    listener.onMobile(jid);
                    break;
            }
        }
        cache.put(jid, status);
    }
}
