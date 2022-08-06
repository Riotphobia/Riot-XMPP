package com.hawolt.xmpp.output;

/**
 * Created: 14/04/2022 01:42
 * Author: Twitter @hawolt
 **/

public interface ISendable {
    String preset();

    void send(IOutput output, Object... o);
}
