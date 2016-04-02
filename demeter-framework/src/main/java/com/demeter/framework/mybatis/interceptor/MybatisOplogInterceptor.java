package com.demeter.framework.mybatis.interceptor;

import com.demeter.models.oplog.Oplog;
import com.demeter.tools.EventBusUtil;
import com.demeter.tools.JsonUtil;
import com.demeter.tools.OplogTracker;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Map;
import java.util.Properties;

/**
 * Created by eric on 4/2/16.
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class MybatisOplogInterceptor implements Interceptor{

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String data = JsonUtil.toJson(invocation.getArgs());
        Map<String, Object> basicInfo = OplogTracker.getLocalMap();
        Oplog oplog = new Oplog();
        oplog.setUsername((String)basicInfo.get(OplogTracker.TRACK_USERNAME));
        oplog.setUid((Integer) basicInfo.get(OplogTracker.TRACK_UID));
        oplog.setOperation((String) basicInfo.get(OplogTracker.TRACK_OPERATION_NAME));
        oplog.setChangeData(data);
        EventBusUtil.post(oplog);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
