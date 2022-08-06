package com.hawolt.event.objects.presence;

import org.json.JSONObject;

/**
 * Created: 27/07/2022 12:51
 * Author: Twitter @hawolt
 **/

public class Regalia {
    private final String bannerType, crestType, selectedPrestigeCrest;

    public Regalia(String bannerType, String crestType, String selectedPrestigeCrest) {
        this.selectedPrestigeCrest = selectedPrestigeCrest;
        this.bannerType = bannerType;
        this.crestType = crestType;
    }

    public String getBannerType() {
        return bannerType;
    }

    public String getCrestType() {
        return crestType;
    }

    public String getSelectedPrestigeCrest() {
        return selectedPrestigeCrest;
    }

    public JSONObject build() {
        return new JSONObject("{\"bannerType\":" + bannerType + ",\"crestType\":" + crestType + ",\"selectedPrestigeCrest\":" + selectedPrestigeCrest + "}");
    }
}
