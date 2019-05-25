package cn.xbmchina.rpc.core;

import io.netty.channel.ChannelFuture;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ChannelManager {


    public static CopyOnWriteArrayList<ChannelFuture> channelFutures = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<String> realServerPath = new CopyOnWriteArrayList<>();
    public static AtomicInteger position = new AtomicInteger(0);

    public static void removeChannel(ChannelFuture channel) {
        channelFutures.remove(channel);
    }

    public static void addChannel(ChannelFuture channel) {
        channelFutures.add(channel);
    }


    public static void clear() {
        channelFutures.clear();
    }

    public static ChannelFuture get(AtomicInteger i) {

        int size = channelFutures.size();
        ChannelFuture channel = null;
        if (position.incrementAndGet() >= size) {
            channel = channelFutures.get(0);
            position =  new AtomicInteger(1);
        } else {
            channel = channelFutures.get(i.getAndIncrement());
        }
        return channel;
    }
}
