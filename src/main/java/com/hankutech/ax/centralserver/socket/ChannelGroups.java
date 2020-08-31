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
        return CLIENT_CHANNEL_MAP.contains(clientId);
    }

    public void broadcast(Object msg, String... clientIds) {
        if (clientIds == null || clientIds.length == 0) {
            clientIds = CLIENT_CHANNEL_MAP.keySet().toArray(new String[0]);
        }

        for (String id : clientIds) {
            try {
                if (contains(id) == false) continue;
                Channel channel = CLIENT_CHANNEL_MAP.get(id);

                if (channel.isWritable()) {
                    channel.write(msg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void broadcast(Object msg) {
        for (String id : CLIENT_CHANNEL_MAP.keySet()) {
            try {
                Channel channel = CLIENT_CHANNEL_MAP.get(id);
                if (channel.isWritable()) {
                    channel.write(msg);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public void clear() {
        CLIENT_CHANNEL_MAP.clear();
    }


    public int size() {
        return CLIENT_CHANNEL_MAP.size();
    }
}
