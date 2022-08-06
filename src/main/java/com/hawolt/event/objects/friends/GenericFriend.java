package com.hawolt.event.objects.friends;

import com.hawolt.event.BaseObject;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created: 12/04/2022 02:06
 * Author: Twitter @hawolt
 **/

public class GenericFriend extends BaseObject {
    protected final SubscriptionType type;
    protected final Object tagline, name;
    protected final String jid, puuid;

    public GenericFriend(JSONObject o) {
        this.jid = o.getString("jid");
        this.puuid = o.getString("puuid");
        this.type = SubscriptionType.of(o.getString("subscription"));
        JSONObject id = o.getJSONObject("id");
        this.name = id.get("name");
        this.tagline = id.get("tagline");
    }

    public String getJID() {
        return jid;
    }

    public String getPUUID() {
        return puuid;
    }

    public Object getName() {
        return name;
    }

    public Object getTagline() {
        return tagline;
    }

    public SubscriptionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BasicFriend{" +
                "jid='" + jid + '\'' +
                ", puuid='" + puuid + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", tagline=" + tagline +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericFriend)) return false;
        GenericFriend friend = (GenericFriend) o;
        return Objects.equals(puuid, friend.puuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(puuid);
    }
}
