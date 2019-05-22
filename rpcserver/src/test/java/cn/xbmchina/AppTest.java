package cn.xbmchina;


import cn.xbmchina.rpc.client.TcpClient;
import cn.xbmchina.rpc.client.entity.ClientRequest;
import cn.xbmchina.rpc.common.Response;
import cn.xbmchina.rpc.entity.Phone;
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



    @Test
    public void  testServer() {
        ClientRequest request = new ClientRequest();
        Phone phone = new Phone();
        phone.setName("华为");
        phone.setPrice(32989.56);
        request.setCommand("cn.xbmchina.rpc.web.PhoneController.save");
        request.setContent(phone);
        Response resp = TcpClient.send(request);
        System.out.println(resp.getResult());
    }
}
