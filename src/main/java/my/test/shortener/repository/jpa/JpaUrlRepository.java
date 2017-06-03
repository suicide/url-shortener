package my.test.shortener.repository.jpa;

import my.test.shortener.repository.ShortUrl;
import my.test.shortener.repository.UrlRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Generated Spring Data repository
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
interface JpaUrlRepository extends CrudRepository<ShortUrl, String>, UrlRepository {

  @Override
  default Optional<String> getLongUrl(String idHash) {
    return Optional.ofNullable(findOne(idHash))
      .map(ShortUrl::getUrl);
  }

  @Override
  default Optional<String> getIdHash(String url) {
    return findByUrl(url)
      .map(ShortUrl::getIdHash);
  }

  @Override
  default void create(ShortUrl shortUrl) {
    save(shortUrl);
  }

  Optional<ShortUrl> findByUrl(String url);
}
