package com.hawolt.xmpp.input;

import com.hawolt.auth.IRiotDataCallback;
import com.hawolt.event.handler.socket.ISocketListener;
import com.hawolt.logger.Logger;
import com.hawolt.xmpp.IActive;
import com.hawolt.xmpp.IdentityCallback;
import com.hawolt.xmpp.output.XMPPOutputHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created: 10/04/2022 15:40
 * Author: Twitter @hawolt
 **/

public class XMPPConnectionHandler implements Runnable {

    private final XMPPOutputHandler output;
    private final ISocketListener listener;
    private final IActive activity;
    private final XMLBuffer buffer;
    private final Socket socket;

    public XMPPConnectionHandler(IActive activeCallback, IdentityCallback identityCallback, IRiotDataCallback riotDataCallback, ISocketListener socketListener, Socket socket) {
        this.output = new XMPPOutputHandler(identityCallback, riotDataCallback, socket, socketListener);
        this.buffer = new XMLBuffer(output);
        this.listener = socketListener;
        this.activity = activeCallback;
        this.socket = socket;
    }

    public void close() throws IOException {
        this.socket.close();
    }

    @Override
    public void run() {
        try (InputStream stream = socket.getInputStream()) {
            int code;
            while ((code = stream.read()) != -1) {
                if (Thread.currentThread().isInterrupted()) break;
                buffer.append(code);
            }
        } catch (IOException e) {
            if (activity.isActive()) Logger.error(e);
        }
        if (activity.isActive()) this.listener.onConnectionIssue();
        else this.listener.onTermination();
    }

    public XMPPOutputHandler getOutput() {
        return output;
    }
}
