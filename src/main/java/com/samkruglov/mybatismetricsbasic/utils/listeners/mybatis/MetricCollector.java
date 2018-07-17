package com.samkruglov.mybatismetricsbasic.utils.listeners.mybatis;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

@Component
@Intercepts({
        //org.apache.ibatis.executor.Executor.update
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        //org.apache.ibatis.executor.Executor.query
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class,
                                                                     Object.class,
                                                                     RowBounds.class,
                                                                     ResultHandler.class,
                                                                     CacheKey.class,
                                                                     BoundSql.class }),
        //org.apache.ibatis.executor.Executor.query
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class,
                                                                     Object.class,
                                                                     RowBounds.class,
                                                                     ResultHandler.class })
})
public class MetricCollector implements Interceptor {
    
    private static final Logger LOG = LoggerFactory.getLogger(MetricCollector.class);
    
    private static final String ANSI_PURPLE = "\u001B[35m";
    
    private static final String ANSI_RESET = "\u001B[0m";
    
    private final MetricRegistry metrics;
    
    public MetricCollector(MetricRegistry metrics) {
        
        this.metrics = metrics;
    }
    
    @Override
    public Object intercept(final Invocation invocation) throws Throwable {
        
        String repoMethodName = Stream.of(invocation.getArgs())
                                      .filter(o -> o instanceof MappedStatement)
                                      .findAny()
                                      .map(o -> ((MappedStatement) o))
                                      .map(MappedStatement::getId)
                                      .map(s -> s.replaceFirst("com\\.samkruglov\\.", ""))
                                      .orElse(null);
    
        // as long as a MappedStatement is present in all "@Signature"s this is not null
        if (Objects.isNull(repoMethodName)) {
            return invocation.proceed();
        }
        LOG.trace("Intercepting invocation for {}{}{}...", ANSI_PURPLE, repoMethodName, ANSI_RESET);
    
        try (Timer.Context context = metrics.timer(repoMethodName).time()) {
            return invocation.proceed();
        }
    }
    
    /**
     * The plugin method is used by the interceptor to encapsulate the target object. In this way, we can return the
     * target object itself or return a proxy. When the proxy is returned, we can intercept the method to call the
     * intercept method.
     */
    @Override
    public Object plugin(final Object target) {
        
        //if we don't user this wrap method then invoke method doesn't get called
        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(final Properties properties) {
    
    }
}
