package com.hawolt.event.objects.conversation.history;

import com.hawolt.event.BaseObject;
import com.hawolt.event.objects.conversation.hint.AbstractConversation;
import org.json.JSONObject;

/**
 * Created: 11/04/2022 22:06
 * Author: Twitter @hawolt
 **/

public abstract class AbstractMessage extends BaseObject {
    protected String from, to, id, type, body;

    public AbstractMessage(JSONObject object) {
        this.id = object.getString("id");
        this.type = object.get("type").toString();
        this.body = object.get("body").toString();
        this.to = object.getString("to");
    }

    protected AbstractMessage(AbstractConversation conversation) {
        this.id = conversation.getId();
        this.type = conversation.getType();
        this.body = conversation.getBody();
        this.from = conversation.getFrom();
        this.to = conversation.getTo();
    }

    public String getTo() {
        return to;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return "AbstractMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
