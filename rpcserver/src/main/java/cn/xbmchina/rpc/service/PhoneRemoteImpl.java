package cn.xbmchina.rpc.service;

import cn.xbmchina.rpc.annotation.Remote;
import cn.xbmchina.rpc.common.Response;
import cn.xbmchina.rpc.common.ResponseUtils;
import cn.xbmchina.rpc.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;

@Remote
public class PhoneRemoteImpl implements PhoneRemote {

    @Autowired
    private PhoneService phoneService;


    @Override
    public Response save(Phone phone) {
        System.out.println("PhoneController===" + phone);
        phoneService.save(phone);
        return ResponseUtils.ofSuccess(11,"请求成功！");

    }
}
