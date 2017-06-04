package my.test.shortener.config;

import my.test.shortener.repository.LocalHashMapUrlRepository;
import my.test.shortener.repository.UrlRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * config for local in-memory repository
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Configuration
@Profile("local")
public class LocalConfig {

  @Bean
  public UrlRepository urlRepository() {
    return new LocalHashMapUrlRepository();
  }

}
