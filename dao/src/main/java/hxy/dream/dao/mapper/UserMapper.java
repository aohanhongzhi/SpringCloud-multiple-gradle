package hxy.dream.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hxy.dream.dao.model.UserModel;

/**
 * @author iris
 */
public interface UserMapper extends BaseMapper<UserModel> {

    /**
     * 忽略逻辑删除，只能写SQL语句
     *
     * @param userModel
     * @return
     */
    int updateWithoutLogicDelete(UserModel userModel);

    UserModel selectUserModel(UserModel userModel);

}
