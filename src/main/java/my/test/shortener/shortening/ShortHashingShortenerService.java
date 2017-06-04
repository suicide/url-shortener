package my.test.shortener.shortening;

import my.test.shortener.repository.ShortUrl;
import my.test.shortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * creates a short hash off of a given url and tries to insert it into the repository if the url not yet in there.
 * if there is a hash collision the hash size is extended and it retries to insert the repo.
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class ShortHashingShortenerService implements ShortenerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShortHashingShortenerService.class);

  static final int HASH_MIN_LENGTH = 5;
  static final int HASH_MAX_LENGTH = 22;

  private Hashing hashing;

  private UrlNormalizer urlNormalizer;

  private UrlRepository urlRepository;

  public ShortHashingShortenerService(Hashing hashing, UrlNormalizer urlNormalizer,
                                      UrlRepository urlRepository) {
    this.hashing = hashing;
    this.urlNormalizer = urlNormalizer;
    this.urlRepository = urlRepository;
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public String shorten(String url) {
    String normalized = urlNormalizer.normalize(url);

    // an implementation without a transaction might bring better db performance especially if there are a lot of
    // collisions or concurrent inserts

    for (int length = HASH_MIN_LENGTH; length <= HASH_MAX_LENGTH; length++) {

      String hash = hashing.hash(normalized, length);

      Optional<String> existingUrl = urlRepository.getLongUrl(hash);
      if (existingUrl.isPresent()) {
        if (existingUrl.get().equals(normalized)) {
          // already in repository
          return hash;
        }

        // collision with another url!
        LOGGER.info("Hash {} caused a collision for url {} and existing url {}", hash, normalized, existingUrl.get());

      } else {
        // insert new url
        urlRepository.create(new ShortUrl(hash, normalized));
        return hash;
      }

    }
    throw new ShortenerServiceException(String.format("unable to create unique hash for url %s", normalized));
  }

}
