package hxy.dream.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hxy.dream.dao.model.UserModel;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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


    @Results(id = "resultMap", value = {
            @Result(column = "address", property = "address", typeHandler = hxy.dream.dao.configuration.mybatis.CustomTypeHandler.class),
            @Result(column = "phone_number", property = "phoneNumber", typeHandler = hxy.dream.dao.configuration.mybatis.CustomTypeHandler.class)
    })
    @Select("select * from user_model where phone_number = #{phoneNumber} or phone_number= #{phoneNumber, typeHandler=hxy.dream.dao.configuration.mybatis.CustomTypeHandler}")
    List<UserModel> listByNumber(UserModel userModel);

}
