package hxy.dream.common.filter.wrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hxy.dream.common.util.SpringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * URL query param。但是测试 表单提交参数没有获取到
 * 请求参数trim处理，非json参数
 */
public class RequestTrimHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final String CHARSET = "utf-8";

    private Map<String, String[]> params = new HashMap<>();


    public RequestTrimHttpServletRequestWrapper(HttpServletRequest request) {
        //将request交给父类，调用对应方法的时候，将其输出
        super(request);
        //持有request中的参数
        this.params.putAll(request.getParameterMap());
        this.modifyParameterValues();
    }

//    /** 这里对json的处理是多余的,jackson在全局反序列化的时候已经处理了
//     * post类型的请求参数必须通过流才能获取到值
//     */
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        //非json类型，直接返回
//        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
//            return super.getInputStream();
//        }
//        //为空，直接返回
//        String json = IOUtils.toString(super.getInputStream(), CHARSET);
//        if (!StringUtils.hasLength(json)) {
//            return super.getInputStream();
//        }
//
//        // FIXME 这里对json的 trim是多余的。
//        ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);
//        JsonNode jsonObject = objectMapper.readTree(json);
//        byte[] bytes = objectMapper.writeValueAsString(jsonObject).getBytes(CHARSET);
//
//        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//        return new MyServletInputStream(bis);
//    }

    /**
     * 将parameter的值去除空格后重写回去
     */
    public void modifyParameterValues() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String[] values = params.get(key);
            if (values != null && values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
            }
            params.put(key, values);
        }
    }

    /**
     * 从当前类中map获取参数
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    @Deprecated
    class MyServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bis;

        public MyServletInputStream(ByteArrayInputStream bis) {
            this.bis = bis;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() {
            return bis.read();
        }
    }

}
