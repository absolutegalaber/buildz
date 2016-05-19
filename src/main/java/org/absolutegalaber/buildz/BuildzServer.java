package org.absolutegalaber.buildz;

import org.absolutegalaber.buildz.aop.LoggingAspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@ComponentScan
public class BuildzServer {
    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

}
