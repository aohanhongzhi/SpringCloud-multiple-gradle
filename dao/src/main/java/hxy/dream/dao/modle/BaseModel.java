package hxy.dream.dao.modle;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

/**
 * @author eric
 * @program eric-dream
 * @description 基础的类模型，增加一些通用字段
 * @date 2020/9/21
 */
public class BaseModel<T extends Model<?>> extends Model<T> {

//    @TableField(fill = FieldFill.INSERT)
//    private String creator;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
//    @TableField(fill = FieldFill.UPDATE)
//    private String updater;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
