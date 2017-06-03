package my.test.shortener.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Local test implementation
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class LocalHashMapUrlRepository implements UrlRepository {

  private final Map<String, String> repo;

  public LocalHashMapUrlRepository() {
    this.repo = new ConcurrentHashMap<>();
  }


  @Override
  public Optional<String> getLongUrl(String idHash) {
    return Optional.ofNullable(repo.get(idHash));
  }

  @Override
  public Optional<String> getIdHash(String url) {
    return repo.entrySet().stream()
      .filter(e -> url.equals(e.getValue()))
      .map(Map.Entry::getKey)
      .findFirst();
  }

  @Override
  public void create(ShortUrl shortUrl) {
    repo.put(shortUrl.getIdHash(), shortUrl.getUrl());
  }
}
