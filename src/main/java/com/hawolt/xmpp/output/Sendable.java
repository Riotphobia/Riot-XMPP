package com.hawolt.xmpp.output;

/**
 * Created: 29/04/2022 12:24
 * Author: Twitter @hawolt
 **/

public class Sendable implements ISendable {

    private final String preset;

    public Sendable(String preset) {
        this.preset = preset;
    }

    @Override
    public String preset() {
        return preset;
    }

    @Override
    public void send(IOutput output, Object... o) {
        output.write(String.format(preset, o));
    }
}
