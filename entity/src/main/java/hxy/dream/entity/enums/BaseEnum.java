package hxy.dream.entity.enums;

/**
 * The interface Enumerator.
 */
// 如果发现注入的bean无法解决json序列化问题，那么可以加上这个注解
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum {
    /**
     * Code integer.
     *
     * @return the integer
     */
    Integer code();

    /**
     * Description string.
     *
     * @return the string
     */
    String description();

    BaseEnum getByCode(Integer code);
}