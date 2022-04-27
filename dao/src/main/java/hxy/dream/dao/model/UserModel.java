package hxy.dream.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import hxy.dream.dao.configuration.mybatis.BasicInfoDTOTypeHandler;
import hxy.dream.dao.configuration.mybatis.CustomTypeHandler;
import hxy.dream.entity.dto.BasicInfoDTO;
import hxy.dream.entity.dto.OtherInfo;
import hxy.dream.entity.enums.GenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author iris
 */
@Data
@TableName(value = "user_model", autoResultMap = true)  //有了这个BaseMapper查询的结果才能解密
@EqualsAndHashCode(callSuper = false)
public class UserModel extends BaseModel<UserModel> {
    /**
     * 参考yaml文件配置id-type: auto
     */
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    Integer age;
    GenderEnum gender;
    String password;

    //有了这个数据库BaseMapper插入的时候才能加密
    @TableField(typeHandler = CustomTypeHandler.class)
    String address;

    /**
     * 注意！！ 必须开启映射注解
     *
     * @TableName(autoResultMap = true)
     * <p>
     * 以下两种类型处理器，二选一 也可以同时存在
     * <p>
     * 注意！！选择对应的 JSON 处理器也必须存在对应依赖包
     */
    @TableField(typeHandler = BasicInfoDTOTypeHandler.class)
    List<BasicInfoDTO> basicInfos;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private OtherInfo otherInfo;
}
