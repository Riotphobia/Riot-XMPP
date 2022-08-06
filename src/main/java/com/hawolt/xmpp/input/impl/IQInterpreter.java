package com.hawolt.xmpp.input.impl;

import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.auth.RateLimitController;
import com.hawolt.auth.login.entitlement.EntitlementUser;
import com.hawolt.event.objects.connection.ChatIdentity;
import com.hawolt.logger.Logger;
import com.hawolt.xmpp.input.AbstractInterpreter;
import com.hawolt.xmpp.output.IOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created: 10/04/2022 20:31
 * Author: Twitter @hawolt
 **/

public class IQInterpreter extends AbstractInterpreter {

    private final Pattern pattern = Pattern.compile("id='(.*?)'");

    public IQInterpreter() {
        map.put("push", new PushInterpreter());
        map.put("_xmpp_bind1", new AbstractInterpreter() {
            @Override
            public void handle(String line, ChatIdentity identity, IRiotDataCallback callback, IOutput output) {
                final EntitlementUser user = new EntitlementUser(callback.getRiotUserData());
                RateLimitController.add(() -> {
                    user.authorize().whenComplete((token, throwable) -> {
                        if (throwable != null) Logger.error(throwable);
                        else {
                            output.write(String.format(preset(), token));
                            unknown("_xmpp_bind1", line, identity, callback, output);
                        }
                    });
                });
            }

            @Override
            public String preset() {
                return "<iq type=\"set\" id=\"xmpp_entitlements_0\"><entitlements xmlns=\"urn:riotgames:entitlements\"><token>%s</token></entitlements></iq><iq type=\"set\" id=\"set_rxep_1\"><rxep xmlns=\"urn:riotgames:rxep\">&lt;last-online-state enabled='true' /&gt;</rxep></iq><iq id=\"_xmpp_session1\" type=\"set\"><session xmlns=\"urn:ietf:params:xml:ns:xmpp-session\"><platform>riot</platform></session></iq>";
            }
        });
        map.put("set_rxep_1", new AbstractInterpreter() {
            @Override
            public void handle(String line, ChatIdentity identity, IRiotDataCallback callback, IOutput output) {
                output.write(String.format(preset(), System.currentTimeMillis()));
            }

            @Override
            public String preset() {
                return "<iq type=\"get\" id=\"2\"><query xmlns=\"jabber:iq:riotgames:roster\" last_state=\"true\"/></iq><iq type=\"get\" id=\"privacy_update_3\"><query xmlns=\"jabber:iq:privacy\"><list name=\"LOL\"/></query></iq><iq type=\"get\" id=\"recent_convos_4\"><query xmlns=\"jabber:iq:riotgames:archive:list\"/></iq><iq id='update_session_active_5' type='set'><query xmlns='jabber:iq:riotgames:session'><session mode='active'/></query></iq><presence id='presence_6'><show>chat</show><status></status><games><keystone><st>chat</st><s.t>%s</s.t><m></m><s.p>keystone</s.p></keystone></games></presence>";
            }
        });
    }

    @Override
    public void handle(String line, ChatIdentity identity, IRiotDataCallback callback, IOutput output) {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) return;
        String id = matcher.group(1);
        Logger.debug("[INTERPRETER-IQ] {}", id);
        if (!map.containsKey(id)) {
            if (id.startsWith("push")) {
                map.get("push").handle(line, identity, callback, output);
            } else unknown(id, line, identity, callback, output);
        } else {
            map.get(id).handle(line, identity, callback, output);
        }
    }

}
