package cn.xbmchina.rpc.entity;

import cn.xbmchina.rpc.common.Response;
import cn.xbmchina.rpc.server.entity.ServerRequest;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataUtils {


    public static Map<String, BeanMethod> beanMap = new HashMap<>();

    private static DataUtils dataUtils = null;

    private DataUtils() {
    }

    public static DataUtils newInstance() {
        if (dataUtils == null) {
            dataUtils = new DataUtils();
        }
        return dataUtils;
    }

    //发射处理业务代码
    public Response process(ServerRequest request) {
        Response result = null;
        BeanMethod beanMethod = beanMap.get(request.getCommand());

        if (beanMethod == null) {
            return null;
        }

        try {
            Object bean = beanMethod.getBean();
            Method m = beanMethod.getMethod();
            Class paramType = m.getParameterTypes()[0];
            Object content = request.getContent();
            Object args = JSONObject.parseObject(JSONObject.toJSONString(content), paramType);

            result = (Response) m.invoke(bean, args);
            result.setId(request.getId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;

    }
}
