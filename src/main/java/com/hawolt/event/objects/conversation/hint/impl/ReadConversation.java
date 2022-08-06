package com.hawolt.event.objects.conversation.hint.impl;

import com.hawolt.event.objects.conversation.hint.AbstractConversation;
import org.json.JSONObject;

/**
 * Created: 11/04/2022 18:07
 * Author: Twitter @hawolt
 **/

public class ReadConversation extends AbstractConversation {

    protected String reader, timestamp;

    public ReadConversation(JSONObject object) {
        super(object);
        JSONObject reader = object.getJSONObject("reader");
        this.timestamp = reader.getString("read");
        this.reader = reader.getString("jid");
    }

    @Override
    public String toString() {
        return "ReadConversation{" +
                ", with='" + with + '\'' +
                ", last='" + last + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", stamp='" + stamp + '\'' +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                ", id=" + id + '\'' +
                ", reader='" + reader + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
