package com.samkruglov.mybatismetricsbasic.configurations;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.jmx.JmxReporter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MetricConfig {
    
    public static final String MYBATIS_JMX_REPORTER_BEAN = "mybatisJmxReporter";
    
    public static final String MYBATIS_SLF4J_REPORTER_BEAN = "mybatisSlf4jReporter";
    
    @Bean
    @ConditionalOnMissingBean
    public MetricRegistry mybatisMetricRegistry() {
        
        return new MetricRegistry();
    }
    
    /**
     * Once the reporter is started, all of the metrics in the registry will become visible via JConsole or VisualVM
     * (if you install the MBeans plugin). We keep it in the context so it lives.
     *
     * Note from DropWizard:
     * We don’t recommend that you try to gather metrics from your production environment. JMX’s RPC API is fragile
     * and bonkers. For development purposes and browsing, though, it can be very useful.
     */
    @Bean(MYBATIS_JMX_REPORTER_BEAN)
    public JmxReporter mybatisJmxReporter(MetricRegistry registry) {
    
        JmxReporter reporter = JmxReporter.forRegistry(registry).build();
        reporter.start();
    
        return reporter;
    }
    
    /*
     * Once the reporter is started, all of the metrics in the registry will be logged once a minute (at the same time).
     */
    @Bean(MYBATIS_SLF4J_REPORTER_BEAN)
    public Slf4jReporter mybatisSlf4jReporter(MetricRegistry registry) {
        
        final Slf4jReporter reporter =
                Slf4jReporter.forRegistry(registry)
                             .outputTo(LoggerFactory.getLogger("metrics.mybatis"))
                             .withLoggingLevel(Slf4jReporter.LoggingLevel.DEBUG)
                             .convertRatesTo(TimeUnit.SECONDS)
                             .convertDurationsTo(TimeUnit.MILLISECONDS)
                             .build();
        reporter.start(1, TimeUnit.MINUTES);
        
        return reporter;
    }
}
