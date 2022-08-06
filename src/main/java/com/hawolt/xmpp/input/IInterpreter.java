package com.hawolt.xmpp.input;

import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.event.objects.connection.ChatIdentity;
import com.hawolt.xmpp.output.IOutput;

/**
 * Created: 10/04/2022 20:31
 * Author: Twitter @hawolt
 **/

public interface IInterpreter {
    void handle(String line, ChatIdentity identity, IRiotDataCallback callback, IOutput output);

    String preset();
}
