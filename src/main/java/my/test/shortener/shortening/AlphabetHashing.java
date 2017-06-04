package my.test.shortener.shortening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Performs a MD5 hashing and encodes the data in a cut-off base62 string
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class AlphabetHashing implements Hashing {

  private static final Logger LOGGER = LoggerFactory.getLogger(AlphabetHashing.class);

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  @Override
  public String hash(String string, int length) {
    assert length > 0;

    String trimmed = StringUtils.trimWhitespace(string);

    if (!StringUtils.hasText(trimmed)) {
      throw new EmptyValueException(String.format("Given String %s is empty", string));
    }

    byte[] bytes = trimmed.getBytes(StandardCharsets.UTF_8);

    byte[] hashBytes = DigestUtils.md5Digest(bytes);

    int size = 0;
    StringBuilder result = new StringBuilder();
    BigInteger hash = new BigInteger(hashBytes);
    BigInteger mod = BigInteger.valueOf(ALPHABET.length());

    while (size < length && !hash.equals(BigInteger.ZERO)) {
      BigInteger remainder = hash.mod(mod);
      hash = hash.divide(mod);

      int rem = remainder.intValue();
      result.append(ALPHABET.charAt(rem));
      size++;
    }

    String hashedString = result.toString();

    LOGGER.debug("Hashed string {} to {}", trimmed, hashedString);

    return hashedString;
  }

}
