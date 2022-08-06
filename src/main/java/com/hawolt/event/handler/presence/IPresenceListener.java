package com.hawolt.event.handler.presence;

import com.hawolt.event.objects.presence.AbstractPresence;
import com.hawolt.event.objects.presence.impl.*;

/**
 * Created: 17/04/2022 23:55
 * Author: Twitter @hawolt
 **/

public interface IPresenceListener {
    void onGamePresence(GamePresence presence);

    void onOnlinePresence(OnlinePresence presence);

    void onMobilePresence(MobilePresence presence);

    void onOfflinePresence(OfflinePresence presence);

    void onDeceivePresence(DeceivePresence presence);

    void onUnknownPresence(AbstractPresence presence);

    void onFakeMobilePresence(FakeMobilePresence presence);
}
