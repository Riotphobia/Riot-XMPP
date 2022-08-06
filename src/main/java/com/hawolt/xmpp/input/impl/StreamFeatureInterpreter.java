package com.hawolt.xmpp.input.impl;

import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.auth.login.LoginValue;
import com.hawolt.event.objects.connection.ChatIdentity;
import com.hawolt.logger.Logger;
import com.hawolt.xmpp.input.AbstractInterpreter;
import com.hawolt.xmpp.output.IOutput;

import java.util.Random;

/**
 * Created: 10/04/2022 20:31
 * Author: Twitter @hawolt
 **/

public class StreamFeatureInterpreter extends AbstractInterpreter {

    public StreamFeatureInterpreter() {
        map.put("mechanisms", new AbstractInterpreter() {
            @Override
            public void handle(String line, ChatIdentity identity, IRiotDataCallback callback, IOutput output) {
                output.write(String.format(preset(), callback.getRiotUserData().get(LoginValue.ACCESS_TOKEN), callback.getXMPPToken()));
            }

            @Override
            public String preset() {
                return "<auth mechanism=\"X-Riot-RSO-PAS\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"><rso_token>%s</rso_token><pas_token>%s</pas_token></auth>";
            }
        });
        map.put("rxep", new AbstractInterpreter() {
            private final Random random = new Random();

            @Override
            public void handle(String line, ChatIdentity identity, IRiotDataCallback callback, IOutput output) {
                output.write(String.format(preset(), 2000000000L + random.nextInt(2000000000)));
            }

            @Override
            public String preset() {
                return "<iq id=\"_xmpp_bind1\" type=\"set\"><bind xmlns=\"urn:ietf:params:xml:ns:xmpp-bind\"><puuid-mode enabled=\"true\"/><resource>RC-%s</resource></bind></iq>";
            }
        });
    }

    @Override
    public void handle(String line, ChatIdentity identity, IRiotDataCallback callback, IOutput output) {
        int openingIndex = line.indexOf('<', 1);
        int spaceIndex = line.indexOf(' ', openingIndex);
        String mapping = line.substring(openingIndex + 1, spaceIndex);
        Logger.debug("[INTERPRETER-STREAM] {}", mapping);
        if (!map.containsKey(mapping)) unknown(mapping, line, identity, callback, output);
        else map.get(mapping).handle(line, identity, callback, output);
    }
}
