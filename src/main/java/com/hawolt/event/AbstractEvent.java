package com.hawolt.event;

import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.event.objects.connection.ChatIdentity;
import com.hawolt.xmpp.output.IOutput;

/**
 * Created: 11/04/2022 00:06
 * Author: Twitter @hawolt
 **/

public class AbstractEvent {

    private final IRiotDataCallback callback;
    private final ChatIdentity identity;
    private final String name, plain;
    private final EventType type;
    private final IOutput output;

    public AbstractEvent(IOutput output, String name, String plain, ChatIdentity identity, IRiotDataCallback callback) {
        this.callback = callback;
        this.identity = identity;
        this.output = output;
        this.plain = plain;
        this.name = name;
        if ("pending_out".equals(name) || "pending_in".equals(name) || "remove".equals(name) || "both".equals(name)) {
            this.type = EventType.FRIEND_REQUEST;
        } else if (name != null && name.startsWith("get_archive")) {
            this.type = EventType.MESSAGE_HISTORY;
        } else if (name != null && name.startsWith("roster_")) {
            this.type = EventType.FRIEND_REQUEST_STATUS;
        } else if (name != null && name.startsWith("message")) {
            this.type = EventType.MESSAGE;
        } else if ("presence".equals(name)) {
            this.type = EventType.PRESENCE;
        } else if ("recent_convos_4".equals(name)) {
            this.type = EventType.RECENT_CONVERSATIONS;
        } else if ("2".equals(name)) {
            this.type = EventType.FRIEND_LIST;
        } else if ("update_client_active_5".equals(name)) {
            this.type = EventType.ON_READY;
        } else if ("_xmpp_session1".equals(name)) {
            this.type = EventType.SUMMONER_NAME;
        } else if ("_xmpp_bind1".equals(name)) {
            this.type = EventType.PERSONAL_INFO;
        } else if ("stream:error".equals(name)) {
            this.type = EventType.SESSION_EXPIRED;
        } else if ("failure".equals(name)) {
            this.type = EventType.FAILURE;
        } else {
            this.type = EventType.UNKNOWN;
        }
    }

    public IRiotDataCallback getCallback() {
        return callback;
    }

    public IOutput getOutput() {
        return output;
    }

    public ChatIdentity getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }

    public String getPlain() {
        return plain;
    }

    public EventType getType() {
        return type;
    }
}
