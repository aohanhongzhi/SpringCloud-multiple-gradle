package hxy.dream.dao.model;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除必须,使用int因为有初始值0
     */
    private int deleted;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
