package com.hawolt.xmpp;

import com.hawolt.event.objects.connection.ChatIdentity;

/**
 * Created: 30/04/2022 14:38
 * Author: Twitter @hawolt
 **/

public interface IdentityCallback {
    ChatIdentity getIdentity();
}
