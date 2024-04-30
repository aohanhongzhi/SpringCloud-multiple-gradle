package hxy.dream.app.service.impl;

import hxy.dream.app.service.DonwloadService;
import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.stereotype.Service;
import cn.zhxu.okhttps.OkHttps;

import java.io.File;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/12/24
 */
@Service
public class DonwloadServiceImpl implements DonwloadService {

    private String url = "https://typora.io/windows/typora-setup-x64.exe";

    @Override
    public BaseResponseVO multipleDonwload() {

        System.out.println("程序开始了");

        long totalSize = OkHttps.sync(url).get()
//                .getBody()
                .close()             // 因为这次请求只是为了获得文件大小，不消费报文体，所以直接关闭
                .getContentLength(); // 获得待下载文件的大小（由于未消费报文体，所以该请求不会消耗下载报文体的时间和网络流量）
        download(totalSize, 0);      // 从第 0 块开始下载
        System.out.println("download跑完了");


        return null;
    }

    void download(long totalSize, int index) {
        System.out.println("===>" + index);
        String filePath = System.getProperty("user.dir") + File.separator + "typora-setup-x64-7.exe";

        long size = 3 * 1024 * 1024;                 // 每块下载 3M
        long start = index * size;
        long end = Math.min(start + size, totalSize);
        OkHttps.sync(url)
                .setRange(start, end)                // 设置本次下载的范围
                .get().getBody()
                .toFile(filePath)      // 下载到同一个文件里
                .setAppended()                       // 开启文件追加模式
                .setOnSuccess((File file) -> {
                    if (end < totalSize) {           // 若未下载完，则继续下载下一块
                        download(totalSize, index + 1);
                    } else {
                        System.out.println("下载完成" + filePath);
                    }
                })
                .start();
    }
}
