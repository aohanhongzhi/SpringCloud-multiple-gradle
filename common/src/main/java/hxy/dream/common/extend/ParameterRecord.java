package hxy.dream.common.extend;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import hxy.dream.common.util.IPAddress;
import hxy.dream.common.util.SpringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 请求信息，参数记录，时间
 *
 * @author eric
 */
@Aspect
@Component
@Order(1)
public class ParameterRecord {

	private static final Logger log = LoggerFactory.getLogger("parameter");

	private final int timeLength = 150;
	private final int dataLength = 250;
	private final int paramLength = 1000;

	String[] ignoreUrl = new String[] { "actuator" };

	ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);

	@Around("execution(* hxy.dream.*.controller.*.*(..))")
	public Object processTx(ProceedingJoinPoint jp) throws Throwable {
		String ip = null, url = null, requestURI = null, targetMethod = null, params = null, resultJson = null,
				requestMethod = null, ipAddr = null;
		long timeConsuming = 0;
		long start = System.currentTimeMillis();

		try {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			if (requestAttributes == null) {
				return null;
			}
			HttpServletRequest request = requestAttributes.getRequest();
			ipAddr = IPAddress.getIpAddr(request);
			requestMethod = request.getMethod();
			requestURI = request.getRequestURI();
			final StringBuffer requestURL = request.getRequestURL();
			log.debug(requestURI);

			final Signature signature1 = jp.getSignature();
			if (signature1 != null && signature1 instanceof MethodSignature) {
				MethodSignature signature = (MethodSignature) signature1;
				Method method = signature.getMethod();
				Class<?> targetClass = method.getDeclaringClass();
				// 拼接请求的接口名 end
				url = getUrl(targetClass, method);

				String name = signature.getName();

				// 接口对应的方法名
				targetMethod = targetClass.getSimpleName() + "." + method.getName() + "()";
			}

			boolean contains = false;
			for (String a : ignoreUrl) {
				if (url != null) {
					contains = url.contains(a);
					if (contains) {
						break;
					}
				} else {
					break;
				}
			}

			final Map<String, String[]> parameterMap = request.getParameterMap();

			if (parameterMap.size() == 0) {
				BufferedReader reader = null;
				try {
					reader = request.getReader();
					String str, wholeStr = "";
					while ((str = reader.readLine()) != null) {
						wholeStr += str;
					}
					if (StringUtils.isNotEmpty(wholeStr)) {
						params = wholeStr;
					}
				} catch (IOException e1) {
					log.error("{}", e1.getMessage(), e1);
				}
			} else {
				params = objectMapper.writeValueAsString(parameterMap);
//                参数记录的目的就是为了数据恢复，所以这里无论多大的参数都要记录下来！
//                if (params.length() > paramLength) {
//                    params = params.substring(0, paramLength) + "....eg....";
//                }
			}

			ip = request.getRemoteHost();

			if (jp != null) {
				boolean paramsExist = true;
				Object[] args = jp.getArgs();
				for (Object object : args) {
					if (object == null) {
						paramsExist = false;
					}
				}
				SourceLocation sourceLocation = jp.getSourceLocation();

				// 这里会抛出Controller的业务异常
				Object result = jp.proceed();
				if (result != null) {
					resultJson = objectMapper.writeValueAsString(result);

					timeConsuming = System.currentTimeMillis() - start;

					if (resultJson.length() > dataLength) {
						resultJson = resultJson.substring(0, dataLength) + "....略....";
					}
					if (url != null && !contains) {

						if (timeConsuming > timeLength) {
							log.warn("\n\n====> visitor host ip :{}\n====> url : {}  uri : {} \n====>method:{}\n====>parameter:{}\n====>response:{}\n====>耗时较长:{}ms",
									ipAddr + " 代理：" + ip, requestMethod + " " + url, requestURI, targetMethod, params,
									resultJson, timeConsuming);
						} else {
							log.info("\n\n====> visitor host ip :{}\n====> url : {}  uri : {} \n====>method:{}\n====>parameter:{}\n====>response:{}\n====>耗时:{}ms",
									ipAddr + " 代理：" + ip, requestMethod + " " + url, requestURI, targetMethod, params,
									resultJson, timeConsuming);
						}
					}
					return result;
				}

			}

		} catch (Throwable throwable) {
//            这里会捕获Controller里面的业务异常直接返回
			timeConsuming = System.currentTimeMillis() - start;
			log.info("\n\n====> visitor host ip : {}\n====> url : {}  uri : {} \n====> method : {}\n====> parameter : {}\n====> exception : {}\n====> 耗时 : {}ms",
					ipAddr + " 代理：" + ip, requestMethod + " " + url, requestURI, targetMethod, params,
					throwable.getMessage(), timeConsuming);
			// 异常继续抛出，交由全局异常捕获再处理
			throw throwable;
		}

		return null;
	}

	private String getUrl(Class<?> targetClass, Method method) {
		// 拼接请求的接口名 start
		StringBuilder requestMapping = new StringBuilder();

		RequestMapping mapping = targetClass.getAnnotation(RequestMapping.class);
		if (mapping != null) {
			String[] value = mapping.value();
			if (value.length > 0) {
				requestMapping.append(value[0]);
			}
		}

		mapping = method.getAnnotation(RequestMapping.class);
		if (mapping != null) {
			String[] value = mapping.value();
			if (value.length > 0) {
				requestMapping.append(value[0]);
			} else {
				String[] path = mapping.path();
				if (path.length > 0) {
					requestMapping.append(path[0]);
				}
			}
		} else {
			PostMapping postMapping = method.getAnnotation(PostMapping.class);
			if (postMapping != null) {
				String[] value = postMapping.value();
				if (value.length > 0) {
					requestMapping.append(value[0]);
				} else {
					String[] path = postMapping.path();
					if (path.length > 0) {
						requestMapping.append(path[0]);
					}
				}
			} else {
				GetMapping getMapping = method.getAnnotation(GetMapping.class);
				if (getMapping != null) {
					String[] value = getMapping.value();
					if (value.length > 0) {
						requestMapping.append(value[0]);
					} else {
						String[] path = getMapping.path();
						if (path.length > 0) {
							requestMapping.append(path[0]);
						}
					}
				} else {
					DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
					if (deleteMapping != null) {
						String[] value = deleteMapping.value();
						if (value.length > 0) {
							requestMapping.append(value[0]);
						} else {
							String[] path = deleteMapping.path();
							if (path.length > 0) {
								requestMapping.append(path[0]);
							}
						}
					} else {
						PutMapping putMapping = method.getAnnotation(PutMapping.class);
						if (putMapping != null) {
							String[] value = putMapping.value();
							if (value.length > 0) {
								requestMapping.append(value[0]);
							} else {
								String[] path = putMapping.path();
								if (path.length > 0) {
									requestMapping.append(path[0]);
								}
							}
						}
					}
				}
			}
		}

		return requestMapping.toString();

	}

	/**
	 * Description: 复制输入流</br>
	 */
	public static InputStream cloneInputStream(ServletInputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buffer)) > -1) {
				byteArrayOutputStream.write(buffer, 0, len);
			}
			byteArrayOutputStream.flush();
		} catch (IOException e) {
			log.error("复制输入流错误{}", e.getMessage(), e);
		}
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

}
