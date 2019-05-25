package cn.xbmchina;

import cn.xbmchina.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("cn.xbmchina")
public class UserApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserApplication.class);
        UserService userService = context.getBean(UserService.class);
        userService.testSavePhone();

    }
}
