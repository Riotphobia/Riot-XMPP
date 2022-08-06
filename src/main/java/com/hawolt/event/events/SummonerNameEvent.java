package com.hawolt.event.events;

import com.hawolt.event.AbstractEvent;
import com.hawolt.event.GenericEvent;
import com.hawolt.event.objects.connection.SummonerName;
import org.json.XML;

/**
 * Created: 30/04/2022 20:54
 * Author: Twitter @hawolt
 **/

public class SummonerNameEvent extends GenericEvent<SummonerName> {

    private final SummonerName name;

    public SummonerNameEvent(AbstractEvent event) {
        super(event);
        this.name = new SummonerName(XML.toJSONObject(event.getPlain()));
    }

    @Override
    public SummonerName get() {
        return name;
    }
}
