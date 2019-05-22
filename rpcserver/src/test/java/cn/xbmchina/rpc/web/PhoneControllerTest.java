package cn.xbmchina.rpc.web;


import cn.xbmchina.rpc.entity.Phone;
import cn.xbmchina.rpc.service.PhoneRemote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PhoneControllerTest.class)
@ComponentScan("cn.xbmchina.rpc")
public class PhoneControllerTest {

    @Autowired
    private PhoneRemote phoneRemote;


    @Test
    public void  testServer() {
//        ClientRequest request = new ClientRequest();
        Phone phone = new Phone();
        phone.setName("华为");
        phone.setPrice(32989.56);
        phoneRemote.save(phone);
//        request.setCommand("cn.xbmchina.rpc.web.PhoneController-save");
//        request.setContent(phone);
//        Response resp = TcpClient.send(request);
//        System.out.println(resp.getResult());
    }

}