package com.hawolt.event.objects.conversation.hint;

import org.json.JSONObject;

/**
 * Created: 11/04/2022 17:27
 * Author: Twitter @hawolt
 **/

public abstract class AbstractConversation {

    protected String with, last, from, to, stamp, type, body, id;

    public AbstractConversation(JSONObject object) {
        this.with = object.getString("with");
        this.last = object.getString("last");
        JSONObject message = object.getJSONObject("message");
        this.stamp = message.getString("stamp");
        this.from = message.getString("from");
        this.to = message.getString("to");
        this.type = message.getString("type");
        this.body = message.get("body").toString();
        this.id = message.getString("id");
    }

    public String getWith() {
        return with;
    }

    public String getLast() {
        return last;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getStamp() {
        return stamp;
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractConversation{" +
                ", with='" + with + '\'' +
                ", last='" + last + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", stamp='" + stamp + '\'' +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                ", id=" + id +
                '}';
    }
}
