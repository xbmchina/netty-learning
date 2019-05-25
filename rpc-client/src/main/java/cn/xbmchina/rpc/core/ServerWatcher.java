package cn.xbmchina.rpc.core;

import cn.xbmchina.rpc.zk.ZkFactory;
import io.netty.channel.ChannelFuture;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

import java.util.List;

public class ServerWatcher implements CuratorWatcher {
    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {

        CuratorFramework client = ZkFactory.create();
        String path = watchedEvent.getPath();
        client.getChildren().usingWatcher(this).forPath(path);
        List<String> serverPaths = client.getChildren().forPath(path);
        ChannelManager.realServerPath.clear();
        for (String serverPath : serverPaths) {
            String[] str = serverPath.split("#");
            int weight = Integer.valueOf(str[2]);
            if (weight >0) {
                for (int i = 0;i<=weight;i++) {
                    ChannelManager.realServerPath.add(str[0] + "#" + str[1]);
                }
            }
            ChannelManager.realServerPath.add(str[0] + "#" + str[1]);
        }

        ChannelManager.clear();
        for (String realServer : ChannelManager.realServerPath) {
            String[] str = realServer.split("#");
            int weight = Integer.valueOf(str[2]);
            if (weight >0) {
                for (int i = 0;i<=weight;i++) {
                    ChannelFuture channelFuture = RPCClient.bootstrap.connect(str[0], Integer.valueOf(str[1]));
                    ChannelManager.addChannel(channelFuture);
                }
            }
            ChannelFuture channelFuture = RPCClient.bootstrap.connect(str[0], Integer.valueOf(str[1]));
            ChannelManager.addChannel(channelFuture);

        }

    }
}
