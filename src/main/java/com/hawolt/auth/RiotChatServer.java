package com.hawolt.auth;

/**
 * Created: 10/04/2022 15:29
 * Author: Twitter @hawolt
 **/

public enum RiotChatServer {
    BR1("br.chat.si.riotgames.com", "br1", 5223),
    AS2("as2.chat.si.riotgames.com","as2", 5223),
    EU3("eu3.chat.si.riotgames.com", "eu3",5223),
    US_BR1("br.chat.si.riotgames.com", "br1",5223),
    EUW1("euw1.chat.si.riotgames.com", "eu1",5223),
    ASIA("jp1.chat.si.riotgames.com", "jp1",5223),
    JP1("jp1.chat.si.riotgames.com", "jp1",5223),
    PBE1("pbe1.chat.si.riotgames.com", "pb1",5223),
    KR1("kr1.chat.si.riotgames.com", "kr1",5223),
    RU1("ru1.chat.si.riotgames.com", "ru1",5223),
    EU("ru1.chat.si.riotgames.com", "ru1",5223),
    SEA4("sa4.chat.si.riotgames.com", "sa4",5223),
    SEA3("sa3.chat.si.riotgames.com", "sa3",5223),
    TR1("tr1.chat.si.riotgames.com", "tr1",5223),
    US2("us2.chat.si.riotgames.com", "us2",5223),
    SEA2("sa2.chat.si.riotgames.com", "sa2",5223),
    SEA1("sa1.chat.si.riotgames.com", "sa1",5223),
    EUN1("eun1.chat.si.riotgames.com", "eu2",5223),
    LA2("la2.chat.si.riotgames.com", "la2",5223),
    OC1("oc1.chat.si.riotgames.com", "oc1",5223),
    LA1("la1.chat.si.riotgames.com", "la1",5223),
    US("la1.chat.si.riotgames.com", "la1",5223),
    NA1("na2.chat.si.riotgames.com", "na1",5223),
    US_LA2("la2.chat.si.riotgames.com", "la2",5223);

    private final String host, domain;
    private final int port;

    RiotChatServer(String host, String domain, int port) {
        this.domain = domain;
        this.host = host;
        this.port = port;
    }

    public String getDomain() {
        return domain;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
