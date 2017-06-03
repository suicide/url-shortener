package my.test.shortener.shortening;

import my.test.shortener.repository.ShortUrl;
import my.test.shortener.repository.UrlRepository;

import java.util.Optional;

/**
 * local test implementation that just counts up on each new url that is being inserted in the repository
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class LocalCountingShortenerService implements ShortenerService {

  private int count = 0;

  private final UrlRepository urlRepository;

  public LocalCountingShortenerService(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  @Override
  public String shorten(String url) {

    Optional<String> idHash = urlRepository.getIdHash(url);

    return idHash
      .orElseGet(() -> {
        String hash = "" + count++;
        urlRepository.create(new ShortUrl(hash, url));
        return hash;
      });
  }
}
