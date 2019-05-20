package cn.xbmchina.rpc.client.entity;

import java.util.concurrent.atomic.AtomicLong;

public class ClientRequest {

    private final long id;
    private Object content;
    private final AtomicLong aLong = new AtomicLong(1);


    public ClientRequest() {
        id = aLong.incrementAndGet();
    }

    public long getId() {
        return id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }


}
