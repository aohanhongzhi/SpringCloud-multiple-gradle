package hxy.dream.entity.dto;

import lombok.Data;

/**
 * 对于 Java 中的记录（Record），你无法直接使用构造函数或公共方法来为其字段赋值，因为记录是不可变的，其字段是 final 的，并且由编译器自动生成 getter 方法。要创建并设置记录的实例，可以使用记录的构造函数。
 * 貌似没有setter方法
 *
 * @param sex
 * @param city
 */
public record OtherInfo(String sex, String city) {

}
///**
// * 其他信息
// */
//@Data
//public class OtherInfo {
//    /**
//     * 性别
//     */
//    private String sex;
//    /**
//     * 居住城市
//     */
//    private String city;
//}