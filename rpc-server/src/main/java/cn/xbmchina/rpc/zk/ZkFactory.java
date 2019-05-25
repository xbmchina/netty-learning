package cn.xbmchina.rpc.zk;

import cn.xbmchina.rpc.common.Const;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZkFactory {

    public static  CuratorFramework client;

    public static CuratorFramework create() {
        if (client == null) {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            client = CuratorFrameworkFactory.newClient(Const.ZK_SERVER, retryPolicy);
            client.start();
            System.out.println(client);
        }
        return client;
    }


    public static void main(String[] args) throws Exception {
        CuratorFramework client = create();
        System.out.println(client);
        client.create().forPath("/netty");
    }



}
