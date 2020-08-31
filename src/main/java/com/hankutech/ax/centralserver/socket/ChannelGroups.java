package com.hankutech.ax.centralserver.socket;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelGroups {

    private ConcurrentHashMap<String, Channel> CLIENT_CHANNEL_MAP = new ConcurrentHashMap<>();

    public void add(String clientId, Channel channel) {
        remove(clientId);
        CLIENT_CHANNEL_MAP.put(clientId, channel);
    }

    public boolean remove(String clientId) {
        if (contains(clientId)) {
            CLIENT_CHANNEL_MAP.remove(clientId);
        }
        return true;
    }

    public boolean contains(String clientId) {
        return CLIENT_CHANNEL_MAP.containsKey(clientId);
    }


    public Channel get(String clientId) {
        if (contains(clientId)) {
            return CLIENT_CHANNEL_MAP.get(clientId);
        }
        return null;
    }

    public void clear() {
        CLIENT_CHANNEL_MAP.clear();
    }


    public int size() {
        return CLIENT_CHANNEL_MAP.size();
    }
}
