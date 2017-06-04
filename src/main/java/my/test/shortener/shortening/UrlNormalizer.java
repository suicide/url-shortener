package my.test.shortener.shortening;

/**
 * normalizes urls
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public interface UrlNormalizer {

  /**
   * normalizes the given url
   * @param url original url
   * @return normalized url
   */
  String normalize(String url);

  class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String message) {
      super(message);
    }

    public InvalidUrlException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
