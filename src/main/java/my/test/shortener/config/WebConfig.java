package my.test.shortener.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
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
   *
   * @param converters
   */
  @Override
  protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
    stringConverter.setWriteAcceptCharset(false);

    converters.add(new FormMessageConverter());
    converters.add(stringConverter);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Void> defaultErrorHandler(Exception e) throws Exception {
    LOGGER.error("An error occured", e);

    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }

  /**
   * decode form requests in body
   */
  private static class FormMessageConverter extends StringHttpMessageConverter {

    public FormMessageConverter() {
      setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
    }

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException,
      HttpMessageNotReadableException {
      String result = super.readInternal(clazz, inputMessage);

      String decoded = URLDecoder.decode(result, "UTF-8");

      if (decoded.endsWith("=")) {
        return decoded.substring(0, decoded.length() - 1);
      }

      return decoded;
    }
  }
}
