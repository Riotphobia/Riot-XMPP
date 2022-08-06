package com.hawolt.xmpp.output;

import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.event.handler.socket.ISocketListener;
import com.hawolt.logger.Logger;
import com.hawolt.misc.StaticConfig;
import com.hawolt.xmpp.IdentityCallback;
import com.hawolt.xmpp.input.InputCallback;
import com.hawolt.xmpp.input.impl.InputInterpreter;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created: 10/04/2022 15:40
 * Author: Twitter @hawolt
 **/

public class XMPPOutputHandler implements InputCallback, Runnable, IOutput {

    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final InputInterpreter interpreter = new InputInterpreter();
    private final List<String> list = new LinkedList<>();
    private final IRiotDataCallback riotDataCallback;
    private final IdentityCallback identityCallback;
    private final ISocketListener listener;
    private final Socket socket;

    public XMPPOutputHandler(IdentityCallback identityCallback, IRiotDataCallback riotDataCallback, Socket socket, ISocketListener listener) {
        this.identityCallback = identityCallback;
        this.riotDataCallback = riotDataCallback;
        this.listener = listener;
        this.socket = socket;
        this.connect();
    }

    public ExecutorService getService() {
        return service;
    }

    public IRiotDataCallback getCallback() {
        return riotDataCallback;
    }

    public InputInterpreter getInterpreter() {
        return interpreter;
    }

    private void connect() {
        write(StaticConfig.SOCKET_CONNECT(this.riotDataCallback.getChatServer()));
    }

    private final Object lock = new Object();

    @Override
    public void run() {
        String line;
        synchronized (lock) {
            if (list.isEmpty() || socket.isClosed()) return;
            line = list.remove(0);
        }
        if (line == null) return;
        try {
            if (!line.equals(" ")) Logger.debug("[OUT] [{}] {}", getIdentifier(), line);
            socket.getOutputStream().write(line.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            if (e instanceof SocketException) {
                listener.onConnectionIssue();
            } else {
                Logger.error(e);
            }
        }
    }

    @Override
    public void write(Object o) {
        synchronized (lock) {
            list.add(o.toString());
        }
        service.execute(this);
    }

    private String getIdentifier() {
        return identityCallback.getIdentity() == null ?
                listener.getConnectionIdentifier() :
                String.join(":", listener.getConnectionIdentifier(), identityCallback.getIdentity().getPUUID());
    }

    @Override
    public void onInput(String line) {
        try {
            Logger.debug("[IN] [{}] {}", getIdentifier(), line);
            service.execute(() -> interpreter.handle(line, identityCallback.getIdentity(), riotDataCallback, this));
        } catch (Exception e) {
            Logger.error(e);
        }
    }
}
