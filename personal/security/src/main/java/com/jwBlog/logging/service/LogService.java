package com.jwBlog.logging.service;

import com.jwBlog.base.service.BaseService;
import com.jwBlog.logging.entity.LogEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

public interface LogService extends BaseService<LogEntity> {
   long saveLong(LogEntity logEntity);
   @Async
   void save(ProceedingJoinPoint joinPoint, LogEntity log);
}
