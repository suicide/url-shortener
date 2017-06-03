package my.test.shortener.repository;

/**
 * a short url consisting of the original url and the id hash
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class ShortUrl {

  private final String idHash;

  private final String url;

  public ShortUrl(String idHash, String url) {
    this.idHash = idHash;
    this.url = url;
  }

  public String getIdHash() {
    return idHash;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ShortUrl shortUrl = (ShortUrl) o;

    if (idHash != null ? !idHash.equals(shortUrl.idHash) : shortUrl.idHash != null) return false;
    return url != null ? url.equals(shortUrl.url) : shortUrl.url == null;
  }

  @Override
  public int hashCode() {
    int result = idHash != null ? idHash.hashCode() : 0;
    result = 31 * result + (url != null ? url.hashCode() : 0);
    return result;
  }
}
