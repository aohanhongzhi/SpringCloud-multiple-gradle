package hxy.dream.app.controller;

import hxy.dream.entity.vo.BaseResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author eric
 * @date 2021/8/13
 */
@RestController
public class LongController {
    @GetMapping(value = "/long")
    public BaseResponseVO testLong() {
        HashMap map = new HashMap();
        map.put("string", "1111111");
        Long l = 9112222222222222222L;
        Long l2 = new Long(9112222222222222222L);
        long l3 = 90071992547409912L;
        long l4 = 9007199254740990L;
        map.put("long", l);
        map.put("Long", l2);
        map.put("超过长度的", l3);
        map.put("不会失去精度的", l4);
        return BaseResponseVO.success(map);
    }
}
