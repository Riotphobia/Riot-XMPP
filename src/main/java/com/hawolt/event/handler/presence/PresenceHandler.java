package com.hawolt.event.handler.presence;

import com.hawolt.event.handler.AbstractObserver;
import com.hawolt.event.objects.presence.AbstractPresence;
import com.hawolt.event.objects.presence.impl.*;

/**
 * Created: 17/04/2022 23:57
 * Author: Twitter @hawolt
 **/

public class PresenceHandler extends AbstractObserver<AbstractPresence, IPresenceListener> {

    @Override
    public void dispatch(AbstractPresence presence) {
        for (IPresenceListener listener : observers) {
            if (presence instanceof DeceivePresence) {
                listener.onDeceivePresence((DeceivePresence) presence);
            } else if (presence instanceof FakeMobilePresence) {
                listener.onFakeMobilePresence((FakeMobilePresence) presence);
            } else if (presence instanceof GamePresence) {
                listener.onGamePresence((GamePresence) presence);
            } else if (presence instanceof MobilePresence) {
                listener.onMobilePresence((MobilePresence) presence);
            } else if (presence instanceof OfflinePresence) {
                listener.onOfflinePresence((OfflinePresence) presence);
            } else if (presence instanceof OnlinePresence) {
                listener.onOnlinePresence((OnlinePresence) presence);
            } else {
                listener.onUnknownPresence(presence);
            }
        }
    }
}
