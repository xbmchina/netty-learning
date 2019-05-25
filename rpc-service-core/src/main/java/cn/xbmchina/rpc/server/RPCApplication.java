package cn.xbmchina.rpc.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("cn.xbmchina.rpc")
public class RPCApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RPCApplication.class);
    }

}
