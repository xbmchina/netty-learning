package cn.xbmchina;


import cn.xbmchina.rpc.client.TcpClient;
import cn.xbmchina.rpc.client.entity.ClientRequest;
import cn.xbmchina.rpc.client.entity.Response;
import cn.xbmchina.rpc.server.entity.ServerRequest;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void  testClient() {
        ClientRequest request = new ClientRequest();
        request.setContent("哈哈哈哈哈哈哈哈哈哈或");
        Response resp = TcpClient.send(request);

        System.out.println(resp.getResult());
    }
}
