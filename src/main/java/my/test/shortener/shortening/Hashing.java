package my.test.shortener.shortening;

/**
 * Hashes a given string to a specified length
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public interface Hashing {

  /**
   * Hashes a given string to a specified length
   *
   * @param string string to hash
   * @param length max length of the hash
   * @return hashed string
   * @throws EmptyValueException if the string is empty
   */
  String hash(String string, int length);

  /**
   * thrown in case the value to be hashed is empty
   */
  class EmptyValueException extends RuntimeException {
    public EmptyValueException(String message) {
      super(message);
    }

    public EmptyValueException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
