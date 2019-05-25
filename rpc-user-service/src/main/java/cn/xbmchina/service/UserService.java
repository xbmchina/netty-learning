package cn.xbmchina.service;

import cn.xbmchina.rpc.annotation.RemoteInvoke;
import cn.xbmchina.rpc.common.Response;
import cn.xbmchina.rpc.entity.Phone;
import cn.xbmchina.rpc.service.PhoneRemote;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @RemoteInvoke
    private PhoneRemote phoneRemote;



    public void testSavePhone() {
        Phone phone = new Phone();
        phone.setName("华为");
        phone.setPrice(1212.1);
        Response result = phoneRemote.save(phone);
        System.out.println(JSONObject.toJSONString(result));
    }



}
