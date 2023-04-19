package hxy.dream.entity.enums;

/**
 * The interface Enumerator.
 */
// 如果发现注入的bean无法解决json序列化问题，那么可以加上这个注解
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//    有必要的话，这个也是可以加一个泛型的，表示支持 Integer或者其他的。
public interface BaseEnum {
    /**
     * Code integer.
     *
     * @return the integer
     */
    Integer code();
//    T code();

    /**
     * Description string.
     *
     * @return the string
     */
    String description();

    String name();

}