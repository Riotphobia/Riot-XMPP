package com.hawolt.event.objects.connection;

import com.hawolt.event.BaseObject;

/**
 * Created: 30/04/2022 14:04
 * Author: Twitter @hawolt
 **/

public class ChatIdentity extends BaseObject {

    private final String identity, jid, puuid, rc;

    public ChatIdentity(String identity) {
        this.identity = identity;
        this.jid = identity.split("/")[0];
        this.puuid = identity.split("@")[0];
        this.rc = identity.split("/")[1];
    }

    public String getIdentity() {
        return identity;
    }

    public String getJID() {
        return jid;
    }

    public String getPUUID() {
        return puuid;
    }

    public String getRC() {
        return rc;
    }

    public static ChatIdentity build(String identity) {
        return new ChatIdentity(identity);
    }

    @Override
    public String toString() {
        return "ChatIdentity{" +
                "identity='" + identity + '\'' +
                ", jid='" + jid + '\'' +
                ", puuid='" + puuid + '\'' +
                ", rc='" + rc + '\'' +
                '}';
    }
}
