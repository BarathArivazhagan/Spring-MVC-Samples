package com.barath.app.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.PublicMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.TraceRepositoryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.TraceWebFilterAutoConfiguration;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.TraceEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collection;

@Configuration
@EnableWebMvc
@Import({
        EndpointAutoConfiguration.class , PublicMetricsAutoConfiguration.class , HealthIndicatorAutoConfiguration.class
, TraceRepositoryAutoConfiguration.class, TraceWebFilterAutoConfiguration.class}
)
public class ActuatorConfig {
	
	@Bean
	public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
		System.out.println("ADDED COMMONS REQUEST LOGGING FILTER");
		CommonsRequestLoggingFilter filter= new CommonsRequestLoggingFilter();
		filter.setIncludeHeaders(true);
		filter.setIncludePayload(true);
		filter.setIncludeQueryString(true);
		filter.setIncludeClientInfo(true);
		filter.setMaxPayloadLength(10000);
		return filter;
	}
	
	@Bean
	public ServletContextRequestLoggingFilter servletRequestfilter() {
		ServletContextRequestLoggingFilter filter = new ServletContextRequestLoggingFilter();
		filter.setIncludeHeaders(true);
		filter.setIncludePayload(true);
		filter.setIncludeQueryString(true);
		filter.setIncludeClientInfo(true);
		return filter;
	}

    @Bean
    @Autowired
    public EndpointHandlerMapping endpointHandlerMapping(Collection<? extends MvcEndpoint> endpoints) {
        return new EndpointHandlerMapping(endpoints);
    }

    @Bean
    @Autowired
    public EndpointMvcAdapter metricsEndPoint(MetricsEndpoint delegate) {
        return new EndpointMvcAdapter(delegate);
    }
    
    @Bean
    public TraceEndpoint traceEndpoint(TraceRepository traceRepository) {
    	return new TraceEndpoint(traceRepository);
    }
    
    @Bean
    @Autowired
    public EndpointMvcAdapter traceEndpointAdapter(TraceEndpoint traceDelegate) {
    	return new EndpointMvcAdapter(traceDelegate);
    }
}
