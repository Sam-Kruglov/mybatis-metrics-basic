package com.samkruglov.mybatismetricsbasic.configurations;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {
    
    public static final String MYBATIS_METRIC_REGISTRY_BEAN = "mybatisMetricRegistry";
    
    public static final String MYBATIS_JMX_REPORTER_BEAN = "mybatisJmxReporter";
    
    @Bean(MYBATIS_METRIC_REGISTRY_BEAN)
    public MetricRegistry mybatisMetricRegistry() {
        
        return new MetricRegistry();
    }
    
    /**
     * Once the reporter is started, all of the metrics in the registry will become visible via JConsole or VisualVM
     * (if you install the MBeans plugin)
     */
    @Bean(MYBATIS_JMX_REPORTER_BEAN)
    public JmxReporter mybatisJmxReporter(@Qualifier(MYBATIS_METRIC_REGISTRY_BEAN) MetricRegistry registry) {
        
        JmxReporter reporter = JmxReporter.forRegistry(registry).build();
        reporter.start();
        
        return reporter;
    }
}
