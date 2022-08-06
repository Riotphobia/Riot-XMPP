package com.hawolt.auth;

import com.hawolt.auth.login.RiotUserData;

/**
 * Created: 10/04/2022 16:44
 * Author: Twitter @hawolt
 **/

public interface IRiotDataCallback {
    String getGameRegion();

    RiotUser getRiotUser();

    RiotUserData getRiotUserData();

    String getXMPPToken();

    RiotChatServer getChatServer();
}
