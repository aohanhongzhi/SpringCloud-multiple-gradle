package hxy.dream.dao.configuration.mybatis;

import hxy.dream.entity.enums.BaseEnum;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @date:2020-11-10 23:16:22
 * @description:打印sql语句
 */
@Intercepts({
        //type指定代理的是那个对象，method指定代理Executor中的那个方法,args指定Executor中的query方法都有哪些参数对象
        //由于Executor中有两个query，因此需要两个@Signature
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),//需要代理的对象和方法
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})//需要代理的对象和方法
})
public class SqlLoggerInterceptor implements Interceptor {
    Logger logger = LogManager.getLogger(SqlLoggerInterceptor.class.getName());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            //获得查询方法的参数，比如selectById(Integer id,String name)，那么就可以获取到四个参数分别是：
            //{id:1,name:"user1",param1:1,param2:"user1"}
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();//获得mybatis的*mapper.xml文件中映射的方法，如：com.best.dao.UserMapper.selectById

        //将参数和映射文件组合在一起得到BoundSql对象
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        //获取配置信息
        Configuration configuration = mappedStatement.getConfiguration();
        Object returnValue = null;

        logger.debug("====>/*---------------java:" + sqlId + "[begin]---------------*/");
        //通过配置信息和BoundSql对象来生成带值得sql语句
        String sql = geneSql(configuration, boundSql);
        //打印sql语句
        logger.info("====> sql:  " + sql);
        //先记录执行sql语句前的时间
        long start = System.currentTimeMillis();
        try {
            //开始执行sql语句
            returnValue = invocation.proceed();
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //记录执行sql语句后的时间
        long end = System.currentTimeMillis();
        //得到执行sql语句的用了多长时间
        long time = (end - start);
        //以毫秒为单位打印
        logger.debug("<== sql执行历时：" + time + "毫秒");
        //返回值，如果是多条记录，那么此对象是一个list，如果是一个bean对象，那么此处就是一个对象，也有可能是一个map
        return returnValue;
    }

    /**
     * 如果是字符串对象则加上单引号返回，如果是日期则也需要转换成字符串形式，如果是其他则直接转换成字符串返回。
     *
     * @param obj
     * @return
     */
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "null";
            }

        }
        return value;
    }

    /**
     * 生成对应的带有值得sql语句
     *
     * @param configuration
     * @param boundSql
     * @return
     */
    public static String geneSql(Configuration configuration, BoundSql boundSql) {

        Object parameterObject = boundSql.getParameterObject();//获得参数对象，如{id:1,name:"user1",param1:1,param2:"user1"}
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();//获得映射的对象参数
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");//获得带问号的sql语句
        if (parameterMappings.size() > 0 && parameterObject != null) {//如果参数个数大于0且参数对象不为空，说明该sql语句是带有条件的
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {//检查该参数是否是一个参数
                //getParameterValue用于返回是否带有单引号的字符串，如果是字符串则加上单引号
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));//如果是一个参数则只替换一次，将问号直接替换成值

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);//将映射文件的参数和对应的值返回，比如：id，name以及对应的值。
                for (ParameterMapping parameterMapping : parameterMappings) {//遍历参数，如:id,name等
                    String propertyName = parameterMapping.getProperty();//获得属性名，如id,name等字符串
                    if (metaObject.hasGetter(propertyName)) {//检查该属性是否在metaObject中

                        Object obj = metaObject.getValue(propertyName);//如果在metaObject中，那么直接获取对应的值

                        // 自定义枚举序列化
                        for (Class<?> typeInterface : parameterMapping.getJavaType().getInterfaces()) {
                            if (typeInterface.equals(BaseEnum.class)) {
                                BaseEnum baseEnum = (BaseEnum) obj;
                                obj = baseEnum.code();
                            }
                        }

                        sql = sql.replaceFirst("\\?", getParameterValue(obj));//然后将问号?替换成对应的值。
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;//最后将sql语句返回
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //properties可以通过
        System.out.println(properties);
    }
}