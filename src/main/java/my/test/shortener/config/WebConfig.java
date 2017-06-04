package my.test.shortener.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * web config
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Configuration
@ControllerAdvice
public class WebConfig extends WebMvcConfigurationSupport {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

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

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Void> defaultErrorHandler(Exception e) throws Exception {
    LOGGER.error("An error occured", e);

    return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
  }
}
