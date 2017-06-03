package my.test.shortener.repository;

import java.util.Optional;

/**
 * Contains id hashes and urls. Id hashes and urls are each to be unique.
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public interface UrlRepository {

  Optional<String> getLongUrl(String idHash);

  Optional<String> getIdHash(String url);

  void create(ShortUrl shortUrl);
}
