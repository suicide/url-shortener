package my.test.shortener.shortening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * TODO: Comment
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class AlphabetHashing {

  private static final Logger LOGGER = LoggerFactory.getLogger(AlphabetHashing.class);

  private static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public String hash(String string, int length) {

    byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

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

    return result.toString();
  }

}
