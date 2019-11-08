package com.mall.jelly.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mall.jelly.annotation.WebLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by 骑着蜗牛去旅行 on 2018/8/17.
 */
@Aspect
@Order(2)
@Component
public class BaseAspect {
    public static final Logger log =  LoggerFactory.getLogger(BaseAspect.class);
    public static final String dateformat = "yyyy:MM:dd HH:mm:ss";
    public static final String STIRNG_START = "\n<<<<<<<<";
    public static final String STIRNG_END = "\n>>>>>>>>>>";
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //execution the scan of pakage 切点package
   /* @Pointcut("execution(* com.komi.bw.cloud.user.controller.*.*.*(..))")
    public void serviceLog(){

    }*/
//    把切面的连接点放在了自定义的注解上
    @Pointcut("@annotation(com.mall.jelly.annotation.WebLog)")
    public void serviceLog(){

    }


    @Around("serviceLog()")
    public Object around(ProceedingJoinPoint joinPoint) { // ProceedingJoinPoint 为JoinPoint 的子类，在父类基础上多了proceed()方法，用于增强切面
        try {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //java reflect相关类，通过反射得到注解
            Method method = signature.getMethod();
            Class<?> targetClass = method.getDeclaringClass();

            StringBuffer classAndMethod = new StringBuffer();

            //获取类注解Log
            WebLog classAnnotation = targetClass.getAnnotation(WebLog.class);
            //获取方法注解Log
            WebLog methodAnnotation = method.getAnnotation(WebLog.class);

            //如果类上Log注解不为空，则执行proceed()
            if (classAnnotation != null) {
                if (classAnnotation.ignore()) {
                    //proceed() 方法执行切面方法，并返回方法返回值
                    return joinPoint.proceed();
                }
                classAndMethod.append(classAnnotation.value()).append("-");
            }

            //如果方法上Log注解不为空，则执行proceed()
            if (methodAnnotation != null) {
                if (methodAnnotation.ignore()) {
                    return joinPoint.proceed();
                }
                classAndMethod.append(methodAnnotation.value());
            }
            //拼凑目标类名和参数名
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String target = targetClass.getName() + "#" + method.getName()+"#"+request.getRequestURI();
            String params = JSONObject.toJSONStringWithDateFormat(joinPoint.getArgs(), dateformat, SerializerFeature.WriteMapNullValue);

            log.info(STIRNG_START + "开始调用-->ip:{},请求方法:{},URL:{}, 参数:{}", request.getRemoteAddr(),request.getMethod(),target, params);

            startTime.set(System.currentTimeMillis());
            //如果类名上和方法上都没有Log注解，则直接执行 proceed()
            Object result = joinPoint.proceed();
            long timeConsuming = System.currentTimeMillis() - startTime.get();

            log.info("\n{} 调用结束<-- {} 返回值:{} 耗时:{}ms" + STIRNG_END, classAndMethod.toString(), target, JSONObject.toJSONStringWithDateFormat(result, dateformat, SerializerFeature.WriteMapNullValue), timeConsuming);
            return result;
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
        }
        return null;
    }
}
