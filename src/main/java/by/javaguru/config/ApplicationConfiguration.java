package by.javaguru.config;

import by.javaguru.utils.ConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "by.javaguru",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class)
        })
public class ApplicationConfiguration {
    @Bean("pool1")
    @Profile("prod")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Connection connectionPoolProd(@Value("${db.url.prod}") String url,
                                         @Value("${db.username.prod}") String username,
                                         @Value("${db.pool.size.prod}") Integer poolSize,
                                         @Value("${db.password.prod}") String password) {
        return ConnectionPool.getConnection(url, username, poolSize, password);
    }

    @Bean("pool2")
    @Profile("test")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Connection connectionPoolTest(@Value("${db.url.test}") String url,
                                         @Value("${db.username.test}") String username,
                                         @Value("${db.pool.size.test}") Integer poolSize,
                                         @Value("${db.password.test}") String password) {
        return ConnectionPool.getConnection(url, username, poolSize, password);
    }
}
