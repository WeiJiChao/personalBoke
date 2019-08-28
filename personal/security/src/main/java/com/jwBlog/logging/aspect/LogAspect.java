package com.jwBlog.logging.aspect;

import com.jwBlog.frame.exception.BadRequestException;
import com.jwBlog.logging.entity.LogEntity;
import com.jwBlog.logging.service.LogService;
import com.jwBlog.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * @author jie
 * @date 2018-11-24
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private LogService logService;

    private long currentTime = 0L;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.jwBlog.frame.aop.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        Object result = null;
        currentTime = System.currentTimeMillis();
        try {
            log.error("ceshi--------------------A");
            result = joinPoint.proceed();
            log.error("ceshi-------------------B");
        } catch (Throwable e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        LogEntity log = new LogEntity("INFO",System.currentTimeMillis() - currentTime);
        logService.save(joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        LogEntity log = new LogEntity("ERROR",System.currentTimeMillis() - currentTime);
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        logService.save((ProceedingJoinPoint)joinPoint, log);
    }
}
