package cn.xbmchina.rpc.client.entity;

public class Response {

    private long id;
    private Object result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
