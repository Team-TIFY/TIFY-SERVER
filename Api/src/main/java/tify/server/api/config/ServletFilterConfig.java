// package tify.server.api.config;
//
//
// import javax.servlet.Filter;
// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;
// import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
// import org.springframework.web.filter.ForwardedHeaderFilter;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
//
// @Configuration
// @RequiredArgsConstructor
// @Profile({"prod", "dev"})
// public class ServletFilterConfig implements WebMvcConfigurer {
//
//    /** 밑에 forwardedHeaderFilter를 @Bean에서 못찾아서 직접 Bean에 등록해줌. */
//    @Bean
//    ForwardedHeaderFilter forwardedHeaderFilter() {
//        return new ForwardedHeaderFilter();
//    }
//
//    private final HttpContentCacheFilter httpContentCacheFilter;
//
//    private final ForwardedHeaderFilter forwardedHeaderFilter;
//
//    public FilterRegistrationBean securityFilterChain(
//            @Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
//                    Filter securityFilter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
//        registration.setOrder(Integer.MAX_VALUE - 3);
//        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
//        return registration;
//    }
//
//    @Bean
//    public FilterRegistrationBean setResourceUrlEncodingFilter() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new ResourceUrlEncodingFilter());
//        registrationBean.setOrder(Integer.MAX_VALUE - 2);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean setForwardedHeaderFilterOrder() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(forwardedHeaderFilter);
//        registrationBean.setOrder(Integer.MAX_VALUE - 1);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean setHttpContentCacheFilterOrder() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(httpContentCacheFilter);
//        registrationBean.setOrder(Integer.MAX_VALUE);
//        return registrationBean;
//    }
// }
