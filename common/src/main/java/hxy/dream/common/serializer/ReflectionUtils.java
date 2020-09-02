package hxy.dream.common.serializer;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.util.proxy.ProxyObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.Advised;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
 
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
/**
 * 反射工具
 * 
 * @author Chenzx
 *
 */
public class ReflectionUtils {
   
   public static ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
 
   // javassist.ClassPool   dubbo中包含此包
   private static ClassPool classPool = ClassPool.getDefault();
 
   public static List<String> getAllFields(Class<?> clazz) {
      if (clazz == Object.class || clazz.isPrimitive())
         return Collections.emptyList();
      try {
         classPool.insertClassPath(new ClassClassPath(clazz));
         CtClass cc = classPool.get(clazz.getName());
         List<CtClass> ctClasses = new ArrayList<CtClass>();
         ctClasses.add(cc);
         while (!(cc = cc.getSuperclass()).getName().equals(Object.class.getName()))
            ctClasses.add(0, cc);
         List<String> fields = new ArrayList<String>();
         for (CtClass ctc : ctClasses) {
            for (CtField cf : ctc.getDeclaredFields()) {
               int accessFlag = cf.getModifiers();
               if (Modifier.isFinal(accessFlag) || Modifier.isStatic(accessFlag))
                  continue;
               fields.add(cf.getName());
            }
         }
         return fields;
      } catch (Exception e) {
         e.printStackTrace();
         return Collections.emptyList();
      }
   }
 
   public static Class<?> getGenericClass(Class<?> clazz) {
      return getGenericClass(clazz, 0);
   }
 
   public static Class<?> getGenericClass(Class<?> clazz, int index) {
      return getGenericClass(clazz.getGenericSuperclass(), index);
   }
 
   public static Class<?> getGenericClass(Type genType, int index) {
      if (genType instanceof ParameterizedType) {
         ParameterizedType pramType = (ParameterizedType) genType;
         Type[] params = pramType.getActualTypeArguments();
         if ((params != null) && (params.length > index))
            return params[index] instanceof Class ? (Class<?>) params[index] : null;
      }
      return null;
   }
 
   public static Class<?> getActualClass(Object object) {
      Class<?> c = object.getClass();
      if (object instanceof ProxyObject)
         c = c.getSuperclass();   
      return c;
   }
 
   public static String[] getParameterNames(Constructor<?> ctor) {
      return getParameterNames(null, ctor);
   }
 
   public static String[] getParameterNames(Method method) {
      return getParameterNames(method, null);
   }
 
   private static String[] getParameterNames(Method method, Constructor<?> ctor) {
      Annotation[][] annotations = method != null ? method.getParameterAnnotations() : ctor.getParameterAnnotations();
      String[] names = new String[annotations.length];
      /*
       * boolean allbind = true; loop: for (int i = 0; i < annotations.length;
       * i++) { Annotation[] arr = annotations[i]; for (Annotation a : arr) {
       * if (a instanceof Param) { String s = ((Param) a).value(); if
       * (StringUtils.isNotBlank(s)) { names[i] = s; continue loop; } } }
       * allbind = false; } if (!allbind) {
       */
      String[] namesDiscovered = method != null ? parameterNameDiscoverer.getParameterNames(method)
            : parameterNameDiscoverer.getParameterNames(ctor);
      if (namesDiscovered == null)
         return null;
      for (int i = 0; i < names.length; i++)
         if (names[i] == null)
            names[i] = namesDiscovered[i];
      // }
      return names;
   }
   public static String[] getParameterNames(JoinPoint jp) {
      if (!jp.getKind().equals(JoinPoint.METHOD_EXECUTION))
         return null;
      Class<?> clz = jp.getTarget().getClass();
      MethodSignature sig = (MethodSignature) jp.getSignature();
      Method method;
      try {
         method = clz.getDeclaredMethod(sig.getName(), sig.getParameterTypes());
         if (method.isBridge())
            method = BridgeMethodResolver.findBridgedMethod(method);
         return getParameterNames(method);
      } catch (Exception e) {
         return null;
      }
   }
 
   public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
      try {
         Field f = clazz.getDeclaredField(name);
         f.setAccessible(true);
         return f;
      } catch (NoSuchFieldException e) {
         if (clazz == Object.class)
            throw e;
         return getField(clazz.getSuperclass(), name);
      }
 
   }
 
   @SuppressWarnings("unchecked")
   public static <T> T getFieldValue(Object o, String name) {
      try {
         Field f = getField(o.getClass(), name);
         return (T) f.get(o);
      } catch (Exception e) {
         throw new RuntimeException(e.getMessage(), e);
      }
   }
 
   public static void setFieldValue(Object o, String name, Object value) {
      try {
         Field f = getField(o.getClass(), name);
         f.set(o, value);
      } catch (Exception e) {
         throw new RuntimeException(e.getMessage(), e);
      }
   }
 
   public static Object getTargetObject(Object proxy) {
      while (proxy instanceof Advised) {
         try {
            return getTargetObject(((Advised) proxy).getTargetSource().getTarget());
         } catch (Exception e) {
            e.printStackTrace();
            return proxy;
         }
      }
      return proxy;
   }
 
   public static String getCurrentMethodName() {
      return Thread.currentThread().getStackTrace()[2].getMethodName();
   }
 
}