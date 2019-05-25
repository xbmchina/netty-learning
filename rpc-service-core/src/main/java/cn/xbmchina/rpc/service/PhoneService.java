package cn.xbmchina.rpc.service;

import cn.xbmchina.rpc.entity.Phone;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {


    public int save(Phone phone) {
        System.out.println(phone.toString());
        return 1;
    }



}
