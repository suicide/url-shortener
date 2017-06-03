package my.test.shortener.config;

import my.test.shortener.api.v1.CreateController;
import my.test.shortener.api.v1.LookupController;
import my.test.shortener.repository.UrlRepository;
import my.test.shortener.shortening.LocalCountingShortenerService;
import my.test.shortener.shortening.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * Main Spring config
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Import({WebConfig.class, LocalConfig.class, MySqlConfig.class})
public class MainConfig {

  @Autowired
  private Environment env;

  @Autowired
  private UrlRepository urlRepository;

  @Bean
  public LookupController lookupController() {
    return new LookupController(urlRepository);
  }

  @Bean
  public CreateController createController() {
    return new CreateController(shortenerService());
  }

  @Bean
  public ShortenerService shortenerService() {
    return new LocalCountingShortenerService(urlRepository);
  }

}
