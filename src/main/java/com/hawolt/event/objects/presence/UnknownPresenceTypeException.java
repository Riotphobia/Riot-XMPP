package com.hawolt.event.objects.presence;

/**
 * Created: 11/04/2022 12:47
 * Author: Twitter @hawolt
 **/

public class UnknownPresenceTypeException extends RuntimeException {

    public UnknownPresenceTypeException(String in) {
        super(in);
    }
}
