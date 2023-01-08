package hxy.dream.common.email;

import hxy.dream.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author eric
 * @program multi-gradle
 * @description 邮件测试
 * @date 2023/1/8
 */
public class EmailTest extends BaseTest {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void emailTest(){
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("aohanhongzhi@qq.com");
        simpleMailMessage.setTo("aohanhongzhi@qq.com");
        simpleMailMessage.setSubject("Happy New Year");
        simpleMailMessage.setText("新年快乐！");
        mailSender.send(simpleMailMessage);

//        原文链接：https://blog.csdn.net/qq_26383975/article/details/121957917
    }
}
