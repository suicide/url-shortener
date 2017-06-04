package my.test.shortener.shortening;

import my.test.shortener.repository.ShortUrl;
import my.test.shortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * TODO: Comment
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class ShortHashingShortenerService implements ShortenerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShortHashingShortenerService.class);

  private static final int HASH_MIN_LENGTH = 5;
  private static final int HASH_MAX_LENGTH = 22;

  private Hashing hashing;

  private UrlRepository urlRepository;

  public ShortHashingShortenerService(Hashing hashing, UrlRepository urlRepository) {
    this.hashing = hashing;
    this.urlRepository = urlRepository;
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public String shorten(String url) {
    // TODO normalize

    // an implementation without a transaction might bring better performance especially if there are a lot of collisions

    for (int length = HASH_MIN_LENGTH; length <= HASH_MAX_LENGTH; length++) {

      String hash = hashing.hash(url, length);

      Optional<String> existingUrl = urlRepository.getLongUrl(hash);
      if (existingUrl.isPresent()) {
        if (existingUrl.get().equals(url)) {
          // already in repository
          return hash;
        }

        // collision with another url!
        LOGGER.info("Hash {} caused a collision for url {}", hash, url);

      } else {
        // insert new url
        urlRepository.create(new ShortUrl(hash, url));
        return hash;
      }

    }
    throw new ShortenerServiceException(String.format("unable to create unique hash for url %s", url));
  }

}
