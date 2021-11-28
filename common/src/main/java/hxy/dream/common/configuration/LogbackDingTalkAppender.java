package hxy.dream.common.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.OkHttps;
import com.ejlchina.okhttps.jackson.JacksonMsgConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author eric
 * @program inspector-server
 * @description 错误消息发送到钉钉上
 * @date 2021/10/28
 */

public class LogbackDingTalkAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final Logger log = LoggerFactory.getLogger(LogbackDingTalkAppender.class);
    private HTTP http = HTTP.builder()
            .baseUrl("http://easyprint.vip:9090/")
            .addMsgConvertor(new JacksonMsgConvertor())
            .build();

    @Override
    protected void append(ILoggingEvent eventObject) {
        Level level = eventObject.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                // 发送到钉钉
                ConcurrentHashMap<String, Object> postBody = new ConcurrentHashMap<>();
                long timeStamp = eventObject.getTimeStamp();

                // 专属业务日志名字，不同业务响应等级划分
                String loggerName = eventObject.getLoggerName();
                String msg = String.format("时间：%s,级别:%s,原因%s", timeStamp, eventObject.getLevel(), eventObject.getFormattedMessage());
                postBody.put("title", loggerName);
                postBody.put("msg", msg);
//                String s = http.async("api/dingtalk/v1/notice").setBodyPara(postBody).bodyType(OkHttps.JSON).post().getResult().getBody().toString();
//                log.debug("钉钉返回信息{}", s);
                break;
            case Level.WARN_INT:
            case Level.INFO_INT:
                //
//                log.info("发送日志信息到钉钉" + eventObject);
                break;
            default:
                // 默认处理
        }

    }
}
