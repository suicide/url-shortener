package my.test.shortener.config;

import my.test.shortener.api.v1.CreateController;
import my.test.shortener.api.v1.LookupController;
import my.test.shortener.repository.LocalHashMapUrlRepository;
import my.test.shortener.repository.UrlRepository;
import my.test.shortener.shortening.LocalCountingShortenerService;
import my.test.shortener.shortening.ShortenerService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Main Spring config
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Configuration
@EnableAutoConfiguration
@Import({WebConfig.class})
public class MainConfig {

  @Bean
  public LookupController lookupController() {
    return new LookupController(urlRepository());
  }

  @Bean
  public CreateController createController() {
    return new CreateController(shortenerService());
  }

  @Bean
  public UrlRepository urlRepository() {
    return new LocalHashMapUrlRepository();
  }

  @Bean
  public ShortenerService shortenerService() {
    return new LocalCountingShortenerService(urlRepository());
  }

}
