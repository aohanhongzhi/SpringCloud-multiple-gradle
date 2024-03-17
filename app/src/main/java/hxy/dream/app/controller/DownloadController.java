package hxy.dream.app.controller;

import hxy.dream.app.service.DonwloadService;
import hxy.dream.entity.vo.BaseResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/12/24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("download")
public class DownloadController {

    @Autowired
    private DonwloadService donwloadService;

    @GetMapping("multi")
    public BaseResponseVO download() {
        return donwloadService.multipleDonwload();
    }
}
