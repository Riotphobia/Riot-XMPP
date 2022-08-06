package com.hawolt.event.events;

import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.connection.ChatIdentity;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created: 30/04/2022 14:03
 * Author: Twitter @hawolt
 **/

public class PersonalInfoEvent extends GenericEvent<ChatIdentity> {

    private ChatIdentity identity;

    public PersonalInfoEvent(AbstractEvent event) {
        super(event);
        JSONObject object = XML.toJSONObject(event.getPlain());
        if (!object.has("iq")) return;
        JSONObject iq = object.getJSONObject("iq");
        if (!iq.has("bind")) return;
        JSONObject bind = iq.getJSONObject("bind");
        if (!bind.has("jid")) return;
        this.identity = ChatIdentity.build(bind.getString("jid"));
    }

    @Override
    public ChatIdentity get() {
        if (identity == null) throw new RuntimeException("NO_IDENTITY_AVAILABLE");
        return identity;
    }
}
