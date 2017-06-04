package my.test.shortener.shortening;

import my.test.shortener.repository.ShortUrl;
import my.test.shortener.repository.UrlRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * the test
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class ShortHashingShortenerServiceTest {

  private ShortHashingShortenerService shortener;

  @Mock
  private Hashing hashing;

  @Mock
  private UrlNormalizer urlNormalizer;

  @Mock
  private UrlRepository urlRepository;

  @Before
  public void setUp() throws Exception {

    MockitoAnnotations.initMocks(this);

    shortener = new ShortHashingShortenerService(hashing, urlNormalizer, urlRepository);

  }

  @Test
  public void shorten_insert() throws Exception {

    String url = "http://google.com";
    String hash = "abcde";
    when(urlNormalizer.normalize(url)).thenReturn(url);
    when(hashing.hash(url, ShortHashingShortenerService.HASH_MIN_LENGTH)).thenReturn(hash);
    when(urlRepository.getLongUrl(hash)).thenReturn(Optional.empty());

    String result = shortener.shorten(url);

    verify(urlRepository).create(eq(new ShortUrl(hash, url)));

    assertThat(result, is(equalTo(hash)));
  }

  @Test
  public void shorten_exists() throws Exception {

    String url = "http://google.com";
    String hash = "abcde";
    when(urlNormalizer.normalize(url)).thenReturn(url);
    when(hashing.hash(url, ShortHashingShortenerService.HASH_MIN_LENGTH)).thenReturn(hash);
    when(urlRepository.getLongUrl(hash)).thenReturn(Optional.of(url));

    String result = shortener.shorten(url);

    verify(urlRepository, never()).create(eq(new ShortUrl(hash, url)));

    assertThat(result, is(equalTo(hash)));
  }

  @Test
  public void shorten_collision() throws Exception {

    String url = "http://google.com";
    String hash = "abcde";
    String hash2 = "abcdef";
    when(urlNormalizer.normalize(url)).thenReturn(url);
    when(hashing.hash(url, ShortHashingShortenerService.HASH_MIN_LENGTH)).thenReturn(hash);
    when(hashing.hash(url, ShortHashingShortenerService.HASH_MIN_LENGTH + 1)).thenReturn(hash2);
    when(urlRepository.getLongUrl(hash)).thenReturn(Optional.of("something"));
    when(urlRepository.getLongUrl(hash2)).thenReturn(Optional.empty());

    String result = shortener.shorten(url);

    verify(urlRepository).create(eq(new ShortUrl(hash2, url)));

    assertThat(result, is(equalTo(hash2)));
  }

  @Test(expected = ShortHashingShortenerService.ShortenerServiceException.class)
  public void shorten_collision_exceeding() throws Exception {

    String url = "http://google.com";
    String hash = "abcde";
    when(urlNormalizer.normalize(url)).thenReturn(url);
    for (int i = ShortHashingShortenerService.HASH_MIN_LENGTH; i <= ShortHashingShortenerService.HASH_MAX_LENGTH; i++) {
      when(hashing.hash(url, i)).thenReturn(hash);
    }
    when(urlRepository.getLongUrl(hash)).thenReturn(Optional.of("something"));

    shortener.shorten(url);
  }

}