package cn.xbmchina.rpc.service;


import cn.xbmchina.rpc.common.Response;
import cn.xbmchina.rpc.entity.Phone;

public interface PhoneRemote {

    public Response save(Phone phone);
}
