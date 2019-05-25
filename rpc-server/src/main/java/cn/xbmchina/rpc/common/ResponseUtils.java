package cn.xbmchina.rpc.common;

public class ResponseUtils {


    public static Response ofSuccess(long id, Object result) {

        return new Response(200, id, result);
    }


    public static Response ofError(long id, Object result) {

        return new Response(500, id, result);

    }

    public static Response ofCommon(int status, long id, Object result) {

        return new Response(status, id, result);
    }


}
