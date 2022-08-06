package com.hawolt.xmpp.input;

import java.util.LinkedList;
import java.util.List;

/**
 * Created: 10/04/2022 14:15
 * Author: Twitter @hawolt
 **/

public class XMLBuffer {

    private final List<Byte> buffer = new LinkedList<>();

    private final InputCallback callback;

    public XMLBuffer(InputCallback callback) {
        this.callback = callback;
    }

    public void append(int character) {
        buffer.add((byte) character);
        if (character == '>') check();
    }

    private String breakpoint;

    private byte[] toByte() {
        byte[] b = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            b[i] = buffer.get(i);
        }
        return b;
    }

    private void check() {
        String buffered = new String(toByte());
        if (buffered.startsWith("</")) {
            reset();
        } else if (buffered.startsWith("<?")) {
            if (breakpoint == null && buffered.endsWith("?>")) {
                int first = buffered.indexOf('?');
                int second = buffered.indexOf('?', first + 1);
                breakpoint = buffered.substring(first, second).split(" ")[1] + ">";
            }
            if (breakpoint != null) {
                if (buffered.endsWith(breakpoint)) {
                    dispatch(buffered);
                }
            }
        } else if (buffered.startsWith("<")) {
            if (breakpoint == null && buffered.endsWith("/>")) {
                breakpoint = "/>";
            } else if (breakpoint == null && buffered.endsWith(">")) {
                int spaceIndex = buffered.indexOf(' ');
                if (spaceIndex == -1) {
                    int first = buffered.indexOf('>');
                    breakpoint = "</" + buffered.substring(1, first) + ">";
                } else {
                    breakpoint = "</" + buffered.substring(1, spaceIndex) + ">";
                }
            }
            if (breakpoint != null) {
                if (buffered.endsWith(breakpoint)) {
                    dispatch(buffered);
                }
            }
        }
    }

    private void dispatch(String line) {
        callback.onInput(line);
        reset();
    }

    private void reset() {
        buffer.clear();
        breakpoint = null;
    }
}
