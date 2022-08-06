package com.hawolt.event.objects.presence;

import com.hawolt.event.objects.presence.impl.*;
import org.json.JSONObject;

/**
 * Created: 13/04/2022 12:36
 * Author: Twitter @hawolt
 **/

public class Presence extends JSONObject {

    public static AbstractPresence create(JSONObject o) {
        if (!o.has("show")) {
            if (o.has("id")) {
                return new DisconnectPresence(o);
            } else {
                return new OfflinePresence(o);
            }
        } else {
            if (o.getString("show").equals("mobile")) {
                if (!o.has("last_online")) {
                    return new FakeMobilePresence(o);
                } else {
                    return new MobilePresence(o);
                }
            } else {
                if (!o.has("game")) {
                    if (!o.has("status")) {
                        return new DeceivePresence(o);
                    } else {
                        return new OnlinePresence(o);
                    }
                } else {
                    return new GamePresence(o);
                }
            }
        }
    }

    public static class Builder {
        private String championId, companionId, damageSkinId, gameQueueType, gameStatus, iconOverride, initRankStat, level, mapId, mapSkinId, masteryScore, profileIcon, puuid, skinVariant, skinname;
        private Regalia regalia;

        public Builder setRegalia(String bannerType, String crestType, String selectedPrestigeCrest) {
            this.regalia = new Regalia(bannerType, crestType, selectedPrestigeCrest);
            return this;
        }

        public Builder setChampionId(String championId) {
            this.championId = championId;
            return this;
        }

        public Builder setCompanionId(String companionId) {
            this.companionId = companionId;
            return this;
        }

        public Builder setDamageSkinId(String damageSkinId) {
            this.damageSkinId = damageSkinId;
            return this;
        }

        public Builder setGameQueueType(String gameQueueType) {
            this.gameQueueType = gameQueueType;
            return this;
        }

        public Builder setGameStatus(String gameStatus) {
            this.gameStatus = gameStatus;
            return this;
        }

        public Builder setIconOverride(String iconOverride) {
            this.iconOverride = iconOverride;
            return this;
        }

        public Builder setInitRankStat(String initRankStat) {
            this.initRankStat = initRankStat;
            return this;
        }

        public Builder setLevel(String level) {
            this.level = level;
            return this;
        }

        public Builder setMapId(String mapId) {
            this.mapId = mapId;
            return this;
        }

        public Builder setMapSkinId(String mapSkinId) {
            this.mapSkinId = mapSkinId;
            return this;
        }

        public Builder setMasteryScore(String masteryScore) {
            this.masteryScore = masteryScore;
            return this;
        }

        public Builder setProfileIcon(String profileIcon) {
            this.profileIcon = profileIcon;
            return this;
        }

        public Builder setPuuid(String puuid) {
            this.puuid = puuid;
            return this;
        }

        public Builder setSkinVariant(String skinVariant) {
            this.skinVariant = skinVariant;
            return this;
        }

        public Builder setSkinname(String skinname) {
            this.skinname = skinname;
            return this;
        }

        public Presence build() {
            Presence presence = new Presence();
            presence.put("championId", championId);
            presence.put("companionId", companionId);
            presence.put("damageSkinId", damageSkinId);
            presence.put("gameQueueType", gameQueueType);
            presence.put("gameStatus", gameStatus);
            presence.put("iconOverride", iconOverride);
            presence.put("initRankStat", initRankStat);
            presence.put("level", level);
            presence.put("mapId", mapId);
            presence.put("mapSkinId", mapSkinId);
            presence.put("masteryScore", masteryScore);
            presence.put("profileIcon", profileIcon);
            presence.put("puuid", puuid);
            presence.put("regalia", regalia.build());
            presence.put("skinVariant", skinVariant);
            presence.put("skinname", skinname);
            return presence;
        }
    }
}
