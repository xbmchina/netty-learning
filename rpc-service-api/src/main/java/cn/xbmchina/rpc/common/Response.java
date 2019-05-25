package cn.xbmchina.rpc.common;

public class Response {

    private int status;
    private long id;
    private Object result;

    public Response() {
    }

    public Response(int status) {
        this.status = status;
    }

    public Response(int status, long id, Object result) {
        this.status = status;
        this.id = id;
        this.result = result;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
