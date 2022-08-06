package com.hawolt.event;

import com.hawolt.event.objects.connection.ChatIdentity;
import com.hawolt.xmpp.output.IOutput;

/**
 * Created: 11/04/2022 00:28
 * Author: Twitter @hawolt
 **/

public abstract class GenericEvent<T> {

    private final long timestamp = System.currentTimeMillis();
    private final ChatIdentity identity;
    private final EventType type;
    private final IOutput output;
    private final String name;

    protected final String plain;

    public GenericEvent(AbstractEvent event) {
        this.identity = event.getIdentity();
        this.output = event.getOutput();
        this.plain = event.getPlain();
        this.name = event.getName();
        this.type = event.getType();
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

    public abstract T get();

    @Override
    public String toString() {
        return "GenericEvent{" + "timestamp=" + timestamp + ", name='" + name + '\'' + ", plain='" + plain + '\'' + ", type=" + type + '}';
    }

}
