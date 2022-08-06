package com.hawolt.event.objects.conversation.history.impl;

import com.hawolt.event.objects.conversation.history.AbstractMessage;
import org.json.JSONObject;

/**
 * Created: 11/04/2022 22:16
 * Author: Twitter @hawolt
 **/

public class FailedMessage extends AbstractMessage {
    private final String puuid, host;

    public FailedMessage(JSONObject object) {
        super(object);
        String[] data = getTo().split("@");
        this.puuid = data[0];
        this.host = data[1];
    }

    public String getPUUID() {
        return puuid;
    }

    public String getHost() {
        return host;
    }
}
