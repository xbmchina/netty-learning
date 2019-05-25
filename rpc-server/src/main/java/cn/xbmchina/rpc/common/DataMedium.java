package cn.xbmchina.rpc.common;

import cn.xbmchina.rpc.annotation.Remote;
import cn.xbmchina.rpc.entity.BeanMethod;
import cn.xbmchina.rpc.entity.DataUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.Map;

@Component
public class DataMedium implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(Remote.class)) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method m : methods) {
                String key = bean.getClass().getInterfaces()[0].getName() + "." + m.getName();
                Map<String, BeanMethod> beanMethodMap = DataUtils.beanMap;
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(m);
                beanMethodMap.put(key, beanMethod);

            }
        }

        return bean;
    }


}
