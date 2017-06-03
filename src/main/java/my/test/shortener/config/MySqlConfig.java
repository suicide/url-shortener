package my.test.shortener.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * TODO: Comment
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Configuration
@Profile("!local")
@EnableAutoConfiguration
@EnableJpaRepositories("my.test.shortener")
@EntityScan(basePackages = {"my.test.shortener.repository"})
public class MySqlConfig {
}