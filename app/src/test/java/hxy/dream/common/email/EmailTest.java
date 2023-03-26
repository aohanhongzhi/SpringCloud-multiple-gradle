package hxy.dream.common.email;

import hxy.dream.BaseTest;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author eric
 * @program multi-gradle
 * @description 邮件测试
 * @date 2023/1/8
 */
public class EmailTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(EmailTest.class);


    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void emailTest() {
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("aohanhongzhi@qq.com");
        simpleMailMessage.setTo("aohanhongzhi@qq.com");
        simpleMailMessage.setSubject("Happy New Year");
        simpleMailMessage.setText("新年快乐！");
        mailSender.send(simpleMailMessage);

//        原文链接：https://blog.csdn.net/qq_26383975/article/details/121957917
    }

    @Test
    public void deleteEmail() {
        try {

            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props);
            //取得pop3协议的邮件服务器
            Store store = null;
            store = session.getStore("pop3");

            //连接pop.qq.com邮件服务器
            store.connect("pop.qq.com", "aohanhongzhi@qq.com", "vtrxapjtpcivdbcb");

            int k = 0;
            while (k < 100) {
                //返回文件夹对象
                Folder folder = store.getFolder("INBOX");
                //设置读写
                folder.open(Folder.READ_WRITE);
                //获取信息
                Message message[] = folder.getMessages();
                int j = 0;
                for (int i = 0; i < message.length && j < 200; i++) {
                    String subject = message[i].getSubject();
                    String from = message[i].getFrom()[0].toString();

                    if (subject.contains("RBLC-ADMIN") || subject.contains("GitHub") || subject.contains("物联网通信计费策略调整") || from.contains("register.csdn.net")|| from.contains("tencent.com") || from.contains("github.com") ) {
                        //设置删除标记
                        message[i].setFlag(Flags.Flag.DELETED, true);
                        j++;
                        log.warn("{}删除邮件 {}", j, subject);
                    } else {
                        log.info(i + ": " + from + "\t" + subject);
                    }
                }
                log.warn("删除{}邮件", j);
                if (j > 0) {
                    folder.close(true);
                } else {
                    folder.close();
                    break;
                }
                k++;
            }

            store.close();
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
