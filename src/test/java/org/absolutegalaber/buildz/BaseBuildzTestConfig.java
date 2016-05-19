package org.absolutegalaber.buildz;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@ComponentScan
@WebAppConfiguration
public class BaseBuildzTestConfig {

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}
