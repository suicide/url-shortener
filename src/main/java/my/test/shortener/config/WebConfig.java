package my.test.shortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * web config
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

  /**
   * disable all other message converters
   * @param converters
   */
  @Override
  protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    stringConverter.setWriteAcceptCharset(false);

    converters.add(stringConverter);
  }
}
