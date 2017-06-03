package my.test.shortener;

import my.test.shortener.config.MainConfig;
import org.springframework.boot.SpringApplication;

/**
 * main class that starts the app
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class Main {

  /**
   * the start
   * @param args app args
   */
  public static void main(String[] args) {

    SpringApplication.run(MainConfig.class, args);

  }

}
