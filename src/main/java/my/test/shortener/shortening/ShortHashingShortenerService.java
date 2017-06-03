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

  private int hashMinLength = 5;
  private int hashMaxLength = 22;

  private AlphabetHashing hashing;

  private UrlRepository urlRepository;

  public ShortHashingShortenerService(AlphabetHashing hashing, UrlRepository urlRepository) {
    this.hashing = hashing;
    this.urlRepository = urlRepository;
  }

  @Override
  @Transactional
  public String shorten(String url) {
    // TODO normalize

    for (int length = hashMinLength; length <= hashMaxLength; length++) {

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
