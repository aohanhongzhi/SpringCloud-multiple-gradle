package hxy.dream.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hxy.dream.app.entity.param.UserParam;
import hxy.dream.app.service.UserService;
import hxy.dream.dao.mapper.UserMapper;
import hxy.dream.dao.model.UserModel;
import hxy.dream.entity.enums.GenderEnum;
import hxy.dream.entity.vo.BaseResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * @author eric
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // @Resource 可以通过上面的 @RequiredArgsConstructor注解来生成构造器。需要final标记
    private final UserMapper userMapper;

    @Override
    public UserModel add(UserParam userParam) {
        UserModel userModel = new UserModel();
        userModel.setName(userParam.getName());
        GenderEnum gender = userParam.getGender();
        userModel.setGender(gender);
        userModel.setAddress(userParam.getAddress());
        userModel.setAge(userParam.getAge());
        if (gender == GenderEnum.UNKNOWN) {
            log.error("性别未知");
        }
        int insert = userMapper.insert(userModel);
        log.debug("\n====>插入影响行数：{}", insert);
        return userModel;
    }

    @Override
    @Cacheable(value = "UserService#36000", key = "'getUser'+#id", unless = "#result == null")
    public UserModel get(String id) {
        UserModel userModel = userMapper.selectById(id);
        return userModel;
    }

    @Override
    public List<UserModel> list() {
        return userMapper.selectList(null);
    }

    @Override
    public BaseResponseVO delete(Integer id) {
        int i = userMapper.deleteById(id);
        if (i > 0) {
            return BaseResponseVO.success();
        } else {
            return BaseResponseVO.error("删除失败");
        }
    }

    /**
     * 判断用户id存在否
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponseVO exist(String id) {

        // 方案1 ，聚簇索引
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
        // 只查询id这一列
        queryWrapper.eq("id", id).select("id");
        UserModel userModel = userMapper.selectOne(queryWrapper);
        if (userModel != null) {
            log.info("用户存在{}", userModel);
        }

        // 方案2 count计数
        QueryWrapper<UserModel> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", id).select("id").last("limit 1");
        Long count = userMapper.selectCount(queryWrapper1);
        if (count > 0) {
            log.info("用户存在{}", count);
        }

        return BaseResponseVO.success();
    }
}
