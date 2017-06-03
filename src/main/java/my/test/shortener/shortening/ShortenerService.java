package my.test.shortener.shortening;

/**
 * handles url shortening
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public interface ShortenerService {

  /**
   * shorten the given url and return an id hash
   * @param url long url to shorten
   * @return id hash
   * @throws InvalidUrlException if the url cannot be processed
   */
  String shorten(String url);


  /**
   * thrown in case the given url is invalid and connect be processed
   */
  class InvalidUrlException extends ShortenerServiceException {
    public InvalidUrlException(String message) {
      super(message);
    }

    public InvalidUrlException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * thrown in case the url could not be shortened
   */
  class ShortenerServiceException extends RuntimeException {
    public ShortenerServiceException(String message) {
      super(message);
    }

    public ShortenerServiceException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
