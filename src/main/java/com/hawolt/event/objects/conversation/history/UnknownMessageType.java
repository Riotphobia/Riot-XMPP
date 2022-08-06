package com.hawolt.event.objects.conversation.history;

/**
 * Created: 11/04/2022 12:47
 * Author: Twitter @hawolt
 **/

public class UnknownMessageType extends RuntimeException {
    public UnknownMessageType(String in) {
        super(in);
    }
}
