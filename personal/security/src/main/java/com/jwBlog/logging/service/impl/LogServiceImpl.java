package com.jwBlog.logging.service.impl;

import cn.hutool.json.JSONObject;
import com.jwBlog.frame.PageUtil;
import com.jwBlog.frame.RequestHolder;
import com.jwBlog.frame.aop.Log;
import com.jwBlog.logging.dao.LogDao;
import com.jwBlog.logging.entity.LogEntity;
import com.jwBlog.logging.service.LogService;
import com.jwBlog.shiro.UserUtils;
import com.jwBlog.utils.dp.StringUtilsNew;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service("logService")
public class LogServiceImpl implements LogService {
	@Autowired
	private LogDao logDao;

	private final String LOGINPATH = "login";

	@Override
	public LogEntity queryObject(Integer id){
		return logDao.queryObject(id);
	}

	@Override
	public List<LogEntity> queryList(Map<String, Object> map){
		return logDao.queryList(map);
	}

	@Override
	public Page queryListByPage(Map<String, Object> map, Pageable pageable) {
		if(pageable.isUnpaged()){
			return (Page)new PageImpl(logDao.queryList(map));
		}
		PageUtil.pageParamToMap(map,pageable);
		return PageableExecutionUtils.getPage(logDao.queryList(map), pageable, () -> {
			return logDao.queryTotal(map);
		});
	}

	@Override
	public List<LogEntity> queryListByBean(LogEntity entity) {
		return logDao.queryListByBean(entity);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return logDao.queryTotal(map);
	}

	@Override
	public LogEntity save(LogEntity logEntity){
		logDao.save(logEntity);
		return logEntity;
	}
	@Override
	public long saveLong(LogEntity logEntity){
		logDao.save(logEntity);
		return logEntity.getId();
	}

	@Override
	public void save(ProceedingJoinPoint joinPoint, LogEntity log) {
		// 获取request
		HttpServletRequest request = RequestHolder.getHttpServletRequest();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Log aopLog = method.getAnnotation(Log.class);
		// 描述
		if (log != null) {
			log.setDescription(aopLog.value());
		}
		// 方法路径
		String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
		String params = "{";
		//参数值
		Object[] argValues = joinPoint.getArgs();
		//参数名称
		String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
		// 用户名
		String username = "";
		if(argValues != null){
			for (int i = 0; i < argValues.length; i++) {
				params += " " + argNames[i] + ": " + argValues[i];
			}
		}
		// 获取IP地址
		log.setRequestIp(StringUtilsNew.getIP(request));

		if(!LOGINPATH.equals(signature.getName())){
			username = UserUtils.getCurrentUserRealName();
		} else {
			try {
				JSONObject jsonObject = new JSONObject(argValues[0]);
				username = jsonObject.get("username").toString();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		log.setMethod(methodName);
		log.setUsername(username);
		log.setParams(params + " }");
		log.setCreateTime(new Timestamp(System.currentTimeMillis()));
		logDao.save(log);
	}

	@Override
	public int update(LogEntity logEntity){
		return logDao.update(logEntity);
	}

	@Override
	public int delete(Integer id){
		return logDao.delete(id);
	}

	@Override
	public int deleteBatch(Integer[] ids){
		return logDao.deleteBatch(ids);
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryCacheCode() {
		return null;
	}

}
