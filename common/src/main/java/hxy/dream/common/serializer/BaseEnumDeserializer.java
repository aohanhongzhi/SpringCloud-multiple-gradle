package hxy.dream.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import hxy.dream.entity.enums.BaseEnum;
import hxy.dream.entity.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 面向接口的枚举反序列化器
 *
 * @author eric
 * @date 2020/9/2
 */
@Slf4j
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {

    @Override
    public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        try {
            // 前端输入的值
            String inputParameter = jp.getText();
            if (inputParameter == null || inputParameter.length() == 0) {
                return null;
            }

            JsonStreamContext parsingContext = jp.getParsingContext();

            //前端注入的对象(ResDTO)
            Object currentValue = parsingContext.getCurrentValue();
            //字段名
            String currentName = parsingContext.getCurrentName();

            JsonStreamContext parent = parsingContext.getParent();

            if (currentValue == null) {
                currentValue = parent.getCurrentValue();
            }
            if (currentName == null) {
                currentName = parent.getCurrentName();
                if (currentName == null) {
                    log.error("\n====>字段名字为空{}", currentName);
                }
            }

            JsonToken currentToken = jp.getCurrentToken();
            if (currentToken == JsonToken.START_OBJECT) {
                // 这里专门解决自定义序列化的结果，再次原样反序列化的处理。适用场景，mq，redis，MongoDB等存储。或者okhttps的restful调用。
                TreeNode treeNode = jp.readValueAsTree();
                if (treeNode != null) {
                    TreeNode code = treeNode.get("code");
                    if (code != null && code instanceof IntNode) {
                        inputParameter = ((IntNode) code).asText();
                    } else {
                        TreeNode description = treeNode.get("description");
                        if (description != null && description instanceof TextNode) {
                            inputParameter = ((TextNode) description).asText();
                        } else {
                            log.error("\n====>该枚举[{}]没有值！", currentName);
                            return null;
                        }
                    }
                } else {
                    return null;
                }
            }
            if (currentValue != null) {
                // 通过对象和属性名获取属性的类型
                Field field = org.springframework.util.ReflectionUtils.findField(currentValue.getClass(), currentName);
                if (field == null) {
                    field = ReflectionUtils.getField(currentValue.getClass(), currentName);
                }

                Class enumClass = field.getType();
                BaseEnum anEnum = DefaultInputJsonToEnum.getEnum(inputParameter, enumClass);
                if (anEnum != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("\n====>测试反序列化枚举[{}]==>[{}.{}]", inputParameter, anEnum.getClass(), anEnum);
                    }
                }
                return anEnum;
            } else {
                throw new BaseException("自定义枚举反序列化错误:json的这个字段[" + parsingContext.getParent() + "]没有值。枚举反序列化失败,注意该属性不可以使用lombok的注解，如@NonNull等");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
