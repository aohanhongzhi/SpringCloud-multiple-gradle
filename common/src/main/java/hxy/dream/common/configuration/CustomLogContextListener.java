package hxy.dream.common.configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * @version 1.0
 * @class: CustomLogContextListener
 * @description: 定义logback 日志监听器，指定日志文件存放目录 主要是解决远程脚本部署的时候发现，日志./logs会新建在用户目录下，而不是应用部署的目录下
 */
public class CustomLogContextListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    /**
     * 存储日志路径标识
     */
    private static final String LOG_PAHT_KEY = "LOG_PATH";

    @Override
    public boolean isResetResistant() {
        return false;
    }

    @Override
    public void onStart(LoggerContext loggerContext) {

    }

    @Override
    public void onReset(LoggerContext loggerContext) {

    }

    @Override
    public void onStop(LoggerContext loggerContext) {

    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }

    /**
     * //        classpath:file:/home/eric/Project/RBLC/spf_rblc_backend/target/rblc-spf.jar!/BOOT-INF/classes!/
     * //        classpath:nested:/home/eric/Project/Java/air-share/build/libs/air-share-0.0.1-SNAPSHOT.jar/!BOOT-INF/classes/!/
     */
    @Override
    public void start() {
        String classpath = null;

        if (true) {
            // SpringBoot 推荐
            try {
                classpath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
                System.out.println("springboot get classpath:" + classpath);
            } catch (FileNotFoundException e) {
                System.err.println("File not found" + e.getMessage());
            }
        } else {
            // 非Spring推荐
            ProtectionDomain protectionDomain = CustomLogContextListener.class.getProtectionDomain();
            CodeSource codeSource = protectionDomain.getCodeSource();
            URL location = codeSource.getLocation();
            System.out.println("java :" + location);
            System.out.println("java classpath:" + location.getPath());
        }

        String logPath = "./logs";

        // 判断是否jar 包启动
        if (classpath != null && (classpath.contains("file:") || classpath.contains("nested:")) && classpath.contains(".jar") && classpath.contains("BOOT-INF")) {
            String jarFilePath = classpath.substring(0, classpath.indexOf(".jar"));
            System.out.println("jarFilePath:" + jarFilePath);
            File file = new File(jarFilePath);
            String currentPath = file.getParent();
//            String currentPath = new File(jarFilePath).getParentFile().getParentFile().getParent();
            // 如果是jar包启动的，那么获取当前jar包程序的路径，作为日志存放的位置
            if (classpath.startsWith("file:")) {
                currentPath = currentPath.replace("file:", "");
            } else if (classpath.startsWith("nested:")) {
                currentPath = currentPath.replace("nested:", "");
            } else {
                int i = currentPath.indexOf(File.separator);
                currentPath = currentPath.substring(i);
            }
            logPath = currentPath + File.separator + "logs";
        }

        // 判断文件夹是否存在，不存在需要新建
        File file = new File(logPath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (mkdirs) {
                System.out.println("日志存储路径创建成功 " + logPath);
            }
        }

        System.out.println("日志存储路径 " + logPath);
        System.setProperty(LOG_PAHT_KEY, logPath);
        Context context = getContext();
        context.putProperty(LOG_PAHT_KEY, logPath);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }
}
