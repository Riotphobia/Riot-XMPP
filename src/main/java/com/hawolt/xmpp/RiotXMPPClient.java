package com.hawolt.xmpp;

import com.hawolt.event.handler.message.IMessageListener;
import com.hawolt.event.handler.presence.IPresenceListener;
import com.hawolt.event.objects.presence.Presence;
import com.hawolt.logger.Logger;
import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.auth.RiotUser;
import com.hawolt.auth.login.LoginValue;
import com.hawolt.auth.login.entitlement.EntitlementUser;
import com.hawolt.event.AbstractEvent;
import com.hawolt.event.AbstractEventHandler;
import com.hawolt.event.EventListener;
import com.hawolt.event.EventType;
import com.hawolt.event.handler.AbstractObserver;
import com.hawolt.event.handler.HandlerType;
import com.hawolt.event.handler.connection.ConnectionHandler;
import com.hawolt.event.handler.connection.IConnectionListener;
import com.hawolt.event.handler.message.MessageHandler;
import com.hawolt.event.handler.presence.PresenceHandler;
import com.hawolt.event.handler.socket.ISocketListener;
import com.hawolt.event.handler.socket.SocketHandler;
import com.hawolt.event.handler.socket.SocketIssue;
import com.hawolt.event.objects.connection.ChatIdentity;
import com.hawolt.event.objects.conversation.hint.RecentConversations;
import com.hawolt.event.objects.conversation.history.AbstractMessage;
import com.hawolt.event.objects.conversation.history.MessageHistory;
import com.hawolt.event.objects.conversation.history.impl.FailedMessage;
import com.hawolt.event.objects.conversation.history.impl.IncomingMessage;
import com.hawolt.event.objects.friends.FriendList;
import com.hawolt.event.objects.friends.GenericFriend;
import com.hawolt.event.objects.friends.IFriendListener;
import com.hawolt.event.objects.friends.status.FriendStatus;
import com.hawolt.event.objects.presence.AbstractPresence;
import com.hawolt.misc.StaticConfig;
import com.hawolt.xmpp.input.XMPPConnectionHandler;
import com.hawolt.xmpp.output.IOutput;
import com.hawolt.xmpp.output.ISendable;
import com.hawolt.xmpp.output.OutputType;
import com.hawolt.xmpp.output.Sendable;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.hawolt.misc.Unsafe.cast;

/**
 * Created: 10/04/2022 15:38
 * Author: Twitter @hawolt
 **/

public class RiotXMPPClient extends AbstractEventHandler implements IActive, IOutput {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Map<String, Consumer<?>> consumers = new HashMap<>();
    private final AtomicInteger integer = new AtomicInteger();

    private final Map<OutputType, ISendable> map = new HashMap<OutputType, ISendable>() {{
        put(OutputType.CUSTOM_PRESENCE, new Sendable("<presence id='presence_%s'><show>%s</show><status>%s</status><games><keystone><st>%s</st><s.t>%s</s.t><m>%s</m><s.p>keystone</s.p></keystone><league_of_legends><s.r>%s</s.r><st>%s</st><s.t>%s</s.t><m>%s</m><p>%s</p><s.p>league_of_legends</s.p><s.c>live</s.c></league_of_legends></games></presence>"));
        put(OutputType.PRESENCE, new Sendable("<presence id='presence_%s'><show>%s</show><status>%s</status><games><keystone><st>%s</st><s.t>%s</s.t><m>%s</m><s.p>keystone</s.p></keystone><league_of_legends><s.r>%s</s.r><st>%s</st><s.t>%s</s.t><m>%s</m><p>{&quot;championId&quot;:&quot;&quot;,&quot;companionId&quot;:&quot;1&quot;,&quot;damageSkinId&quot;:&quot;1&quot;,&quot;gameQueueType&quot;:&quot;&quot;,&quot;gameStatus&quot;:&quot;outOfGame&quot;,&quot;iconOverride&quot;:&quot;&quot;,&quot;initRankStat&quot;:&quot;0&quot;,&quot;level&quot;:&quot;30&quot;,&quot;mapId&quot;:&quot;&quot;,&quot;mapSkinId&quot;:&quot;1&quot;,&quot;masteryScore&quot;:&quot;0&quot;,&quot;profileIcon&quot;:&quot;%s&quot;,&quot;puuid&quot;:&quot;%s&quot;,&quot;regalia&quot;:&quot;{\\&quot;bannerType\\&quot;:2,\\&quot;crestType\\&quot;:1,\\&quot;selectedPrestigeCrest\\&quot;:0}&quot;,&quot;skinVariant&quot;:&quot;&quot;,&quot;skinname&quot;:&quot;&quot;}</p><s.p>league_of_legends</s.p><s.c>live</s.c></league_of_legends></games></presence>"));
        put(OutputType.REFRESH_SESSION, new Sendable("<iq from=\"%s\" type=\"set\" id=\"0\"><query xmlns=\"jabber:iq:riotgames:rso\"><access-token>%s</access-token></query></iq><iq type=\"set\" id=\"xmpp_entitlements_0\"><entitlements xmlns=\"urn:riotgames:entitlements\"><token>%s</token></entitlements></iq>"));
        put(OutputType.FRIEND_ADD_NAME, new Sendable("<iq type=\"set\" id=\"roster_add_%s\"><query xmlns=\"jabber:iq:riotgames:roster\"><item subscription=\"pending_out\" name=\"%s\"><lol name=\"%s\"/></item></query></iq>"));
        put(OutputType.FRIEND_ADD_TAG, new Sendable("<iq type=\"set\" id=\"roster_add_%s\"><query xmlns=\"jabber:iq:riotgames:roster\"><item subscription=\"pending_out\"><id name=\"%s\" tagline=\"%s\"/></item></query></iq>"));
        put(OutputType.FRIEND_BLOCK, new Sendable("<iq type=\"set\" id=\"%s\" to=\"pvp.net\"><query xmlns=\"jabber:iq:privacy\"><add name=\"LOL\"><item value=\"%s\" action=\"deny\" order=\"1\" type=\"jid\"/></add></query></iq>"));
        put(OutputType.FRIEND_REMOVE, new Sendable("<iq type=\"set\" id=\"roster_remove_%s\"><query xmlns=\"jabber:iq:riotgames:roster\"><item jid=\"%s\" subscription=\"remove\"/></query></iq>"));
        put(OutputType.ARCHIVE, new Sendable("<iq type=\"get\" id=\"%s\"><query xmlns=\"jabber:iq:riotgames:archive\"><with>%s</with></query></iq>"));
        put(OutputType.MESSAGE, new Sendable("<message id=\"%s:%s\" to=\"%s\" type=\"chat\"><body>%s</body></message>"));
    }};

    private final Map<HandlerType, AbstractObserver<?, ?>> handlers = new HashMap<HandlerType, AbstractObserver<?, ?>>() {{
        put(HandlerType.CONNECTION, new ConnectionHandler());
        put(HandlerType.PRESENCE, new PresenceHandler());
        put(HandlerType.MESSAGE, new MessageHandler());
        put(HandlerType.SOCKET, new SocketHandler());
    }};

    private final XMPPConnectionHandler connectionHandler;
    private final ScheduledFuture<?> heartbeat, session;
    private final Future<?> in;

    private ChatIdentity identity;
    private FriendList list;

    // TODO rework this workaround somehow
    private final List<GenericFriend> pipe = new LinkedList<>();
    private final Object lock = new Object();

    public RiotXMPPClient(IRiotDataCallback callback, Socket socket) {
        this(callback, socket, null);
    }

    public RiotXMPPClient(IRiotDataCallback callback, Socket socket, ISocketListener listener) {
        this.addHandler(EventType.SESSION_EXPIRED, event -> onSessionExpired());
        this.addHandler(EventType.PERSONAL_INFO, (EventListener<ChatIdentity>) identity -> {
            this.identity = identity;
            synchronized (lock) {
                if (this.list == null || this.identity == null) return;
                this.dispatch("update_client_active_5", "CLIENT_READY");
            }
        });
        this.addHandler(EventType.MESSAGE, (EventListener<AbstractMessage>) message -> handlers.get(HandlerType.MESSAGE).dispatch(cast(message)));
        if (listener != null) this.handlers.get(HandlerType.SOCKET).addObserver(cast(listener));
        this.connectionHandler = new XMPPConnectionHandler(this, this, callback, this, socket);
        this.connectionHandler.getOutput().getInterpreter().setCallback(this);
        this.heartbeat = this.scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (!socket.isClosed()) write(StaticConfig.HEARTBEAT);
        }, 30, 30, TimeUnit.SECONDS);
        this.session = this.scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (!socket.isClosed()) {
                callback.getRiotUser().login().whenComplete(((data, throwable) -> {
                    if (throwable != null) Logger.error(throwable);
                    else {
                        EntitlementUser user = new EntitlementUser(data);
                        try {
                            String token = user.authorize().get(10000L, TimeUnit.MILLISECONDS);
                            refreshSession(identity.getIdentity(), data.get(LoginValue.ACCESS_TOKEN), token);
                        } catch (InterruptedException | ExecutionException | TimeoutException e) {
                            Logger.error(e);
                        }
                    }
                }));
            }
        }, 1, 1, TimeUnit.HOURS);
        this.in = this.executorService.submit(connectionHandler);
        this.addHandler(EventType.RECENT_CONVERSATIONS, (EventListener<RecentConversations>) conversations -> {
            conversations.getList().forEach(conversation -> {
                String jid = identity.getJID();
                if (conversation.getFrom().equals(jid)) return;
                IncomingMessage message = IncomingMessage.construct(conversation);
                handlers.get(HandlerType.MESSAGE).dispatch(cast(message));
            });
        });
        this.addHandler(EventType.PRESENCE, (EventListener<AbstractPresence>) presence -> {
            String jid = identity.getJID();
            if (presence.getFrom().equals(jid)) return;
            handlers.get(HandlerType.CONNECTION).dispatch(cast(presence));
            handlers.get(HandlerType.PRESENCE).dispatch(cast(presence));
        });
        this.addHandler(EventType.FRIEND_LIST, (EventListener<FriendList>) list -> {
            this.list = list;
            while (!pipe.isEmpty()) {
                GenericFriend friend = pipe.remove(0);
                switch (friend.getType()) {
                    case REMOVE:
                        this.list.remove(friend);
                        break;
                    case BOTH:
                    case PENDING_IN:
                    case PENDING_OUT:
                        this.list.add(friend);
                        break;
                }
            }
            synchronized (lock) {
                if (this.list == null || this.identity == null) return;
                this.dispatch("update_client_active_5", "CLIENT_READY");
            }
        });
        this.addHandler(EventType.FRIEND_REQUEST, (EventListener<GenericFriend>) friend -> {
            if (this.list != null) {
                switch (friend.getType()) {
                    case REMOVE:
                        this.list.remove(friend);
                        break;
                    case BOTH:
                    case PENDING_IN:
                    case PENDING_OUT:
                        this.list.add(friend);
                        break;
                }
            } else {
                pipe.add(friend);
            }
        });
        this.addHandler(EventType.FRIEND_REQUEST_STATUS, (EventListener<FriendStatus>) status -> {
            Optional.ofNullable(consumers.get(status.getId())).ifPresent(consumer -> {
                consumer.accept(cast(status));
                consumers.remove(status.getId());
            });
        });
        this.addHandler(EventType.MESSAGE, (EventListener<AbstractMessage>) message -> {
            if (message instanceof FailedMessage) return;
            String identifier = message.getId();
            Optional.ofNullable(consumers.get(identifier)).ifPresent(consumer -> {
                consumer.accept(cast(message));
                consumers.remove(identifier);
            });
        });
        this.addHandler(EventType.MESSAGE_HISTORY, (EventListener<MessageHistory>) history -> {
            Optional.ofNullable(consumers.get(history.getId())).ifPresent(consumer -> {
                consumer.accept(cast(history));
                consumers.remove(history.getId());
            });
        });
    }

    private void dispatch(String name, String plain) {
        onEvent(new AbstractEvent(this, name, plain, identity, connectionHandler.getOutput().getCallback()));
    }

    public XMPPConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public FriendList getFriendList() {
        return list;
    }

    public AtomicInteger getIdManager() {
        return integer;
    }


    public void addFriendListener(IFriendListener listener) {
        this.list.addObserver(listener);
    }

    private void refreshSession(String identifier, String token, String entitlement) {
        map.get(OutputType.REFRESH_SESSION).send(this, identifier, token, entitlement);
    }

    public void loadChat(String jid, Consumer<MessageHistory> consumer) {
        int identifier = integer.incrementAndGet();
        String id = String.join("_", "get", "archive", String.valueOf(identifier), jid);
        consumers.put(id, consumer);
        map.get(OutputType.ARCHIVE).send(this, id, jid);
    }

    public void blockUser(String jid) {
        map.get(OutputType.FRIEND_BLOCK).send(this, "privacy_add_34", jid);
    }

    public void sendMessage(String jid, String message, Consumer<AbstractMessage> consumer) {
        long timestamp = System.currentTimeMillis();
        int identifier = integer.incrementAndGet();
        String id = String.join(":", String.valueOf(timestamp), String.valueOf(identifier));
        if (consumer != null) consumers.put(id, consumer);
        ISendable sendable = map.get(OutputType.MESSAGE);
        String plain = String.format(sendable.preset(), timestamp, identifier, jid, message);
        sendable.send(this, timestamp, identifier, jid, message);
        dispatch("message_" + id, plain);
    }

    public void setPresence(String type, String status, long icon) {
        String jid = identity.getJID();
        long timestamp = System.currentTimeMillis();
        String region = connectionHandler.getOutput().getCallback().getGameRegion();
        map.get(OutputType.PRESENCE).send(this, integer.incrementAndGet(), type, status, type, timestamp, status, region, type, timestamp, status, icon, jid);
    }

    public void setCustomPresence(String type, String status, Presence presence) {
        long timestamp = System.currentTimeMillis();
        String region = connectionHandler.getOutput().getCallback().getGameRegion();
        map.get(OutputType.CUSTOM_PRESENCE).send(this, integer.incrementAndGet(), type, status, type, timestamp, status, region, type, timestamp, status, presence.toString());
    }

    public void addFriendByName(String name) {
        addFriendByName(name, null);
    }

    public void addFriendByName(String name, Consumer<FriendStatus> consumer) {
        int identifier = integer.incrementAndGet();
        if (consumer != null) {
            String id = "roster_add_" + identifier;
            consumers.put(id, consumer);
        }
        map.get(OutputType.FRIEND_ADD_NAME).send(this, identifier, name, name);
    }

    public void addFriendByTag(String name, String tagline) {
        addFriendByTag(name, tagline, null);
    }

    public void addFriendByTag(String name, String tagline, Consumer<FriendStatus> consumer) {
        int identifier = integer.incrementAndGet();
        if (consumer != null) {
            String id = "roster_add_" + identifier;
            consumers.put(id, consumer);
        }
        map.get(OutputType.FRIEND_ADD_TAG).send(this, identifier, name, tagline);
    }

    public void removeFriend(String jid) {
        removeFriend(jid, null);
    }

    public void removeFriend(String jid, Consumer<FriendStatus> consumer) {
        int identifier = integer.incrementAndGet();
        if (consumer != null) consumers.put("roster_remove_" + identifier, consumer);
        map.get(OutputType.FRIEND_REMOVE).send(this, identifier, jid);
    }

    public void terminate() throws IOException {
        Logger.warn("[TERMINATE] {}", getConnectionIdentifier());
        this.scheduledExecutorService.shutdown();
        this.getConnectionHandler().getOutput().getService().shutdown();
        this.heartbeat.cancel(true);
        this.session.cancel(true);
        this.in.cancel(true);
        this.executorService.shutdown();
        this.getConnectionHandler().close();
    }

    public void addConnectionListener(IConnectionListener listener) {
        addListener(HandlerType.CONNECTION, cast(listener));
    }

    public void addPresenceListener(IPresenceListener listener) {
        addListener(HandlerType.PRESENCE, cast(listener));
    }

    public void addMessageListener(IMessageListener listener) {
        addListener(HandlerType.MESSAGE, cast(listener));
    }

    public void addSocketListener(ISocketListener listener) {
        addListener(HandlerType.SOCKET, cast(listener));
    }

    public void addListener(HandlerType type, AbstractObserver<?, ?> listener) {
        this.handlers.get(type).addObserver(cast(listener));
    }

    @Override
    protected void onException(Exception e) {
        dispatch("failure", e.getMessage());
    }

    @Override
    protected void onUnavailableIdentity() {
        handlers.get(HandlerType.SOCKET).dispatch(cast(SocketIssue.NO_IDENTITY));
    }

    @Override
    public String getConnectionIdentifier() {
        if (connectionHandler == null) return "?";
        RiotUser user = connectionHandler.getOutput().getCallback().getRiotUser();
        if (user == null) return "?";
        return String.format("%s:%s", user.getUsername(), user.getPassword());
    }

    @Override
    public void onConnectionIssue() {
        handlers.get(HandlerType.SOCKET).dispatch(cast(SocketIssue.SOCKET_CLOSED));
    }

    @Override
    public void onSessionExpired() {
        handlers.get(HandlerType.SOCKET).dispatch(cast(SocketIssue.SESSION_EXPIRED));
    }


    @Override
    public void onTermination() {

    }

    @Override
    public ChatIdentity getIdentity() {
        return identity;
    }

    @Override
    public boolean isActive() {
        return !scheduledExecutorService.isShutdown();
    }

    @Override
    public void write(Object o) {
        connectionHandler.getOutput().write(o);
    }
}
