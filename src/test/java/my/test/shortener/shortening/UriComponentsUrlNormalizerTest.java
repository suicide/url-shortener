package my.test.shortener.shortening;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * the test
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class UriComponentsUrlNormalizerTest {

  private UriComponentsUrlNormalizer normalizer;

  @Before
  public void setUp() throws Exception {
    normalizer = new UriComponentsUrlNormalizer();
  }

  @Test
  public void normalize_nothing() throws Exception {

    String orignalUrl = "http://google.com";
    String targetUrl = "http://google.com";

    String result = normalizer.normalize(orignalUrl);

    assertThat(result, is(equalTo(targetUrl)));
  }

  @Test
  public void normalize_default_scheme() throws Exception {

    String orignalUrl = "google.com";
    String targetUrl = "http://google.com";

    String result = normalizer.normalize(orignalUrl);

    assertThat(result, is(equalTo(targetUrl)));
  }

  @Test
  public void normalize_nothing_path() throws Exception {

    String orignalUrl = "http://google.com/Something/bla1";
    String targetUrl = "http://google.com/Something/bla1";

    String result = normalizer.normalize(orignalUrl);

    assertThat(result, is(equalTo(targetUrl)));
  }

  @Test
  public void normalize_nothing_path_parameters() throws Exception {

    String orignalUrl = "http://google.com/Something/bla1?hello=world&id=2#blubb";
    String targetUrl = "http://google.com/Something/bla1?hello=world&id=2#blubb";

    String result = normalizer.normalize(orignalUrl);

    assertThat(result, is(equalTo(targetUrl)));
  }

  @Test
  public void normalize_case() throws Exception {

    String orignalUrl = "http://gOOgle.com";
    String targetUrl = "http://google.com";

    String result = normalizer.normalize(orignalUrl);

    assertThat(result, is(equalTo(targetUrl)));
  }

  @Test
  public void normalize_case_path() throws Exception {

    String orignalUrl = "http://gOOgle.com/Something/bla1";
    String targetUrl = "http://google.com/Something/bla1";

    String result = normalizer.normalize(orignalUrl);

    assertThat(result, is(equalTo(targetUrl)));
  }

  @Test
  public void normalize_case_path_parameters() throws Exception {

    String orignalUrl = "http://gOOgle.com/Something/bla1?hello=world&id=2#blubb";
    String targetUrl = "http://google.com/Something/bla1?hello=world&id=2#blubb";

    String result = normalizer.normalize(orignalUrl);

    assertThat(result, is(equalTo(targetUrl)));
  }

  @Test(expected = UrlNormalizer.InvalidUrlException.class)
  public void normalize_invalid_empty() throws Exception {

    String orignalUrl = "";

    normalizer.normalize(orignalUrl);
  }

  @Test(expected = UrlNormalizer.InvalidUrlException.class)
  public void normalize_invalid_null() throws Exception {

    String orignalUrl = null;

    normalizer.normalize(orignalUrl);
  }

  @Test(expected = UrlNormalizer.InvalidUrlException.class)
  public void normalize_invalid_scheme() throws Exception {

    String orignalUrl = "ssh://bla.com";

    normalizer.normalize(orignalUrl);
  }

  @Test(expected = UrlNormalizer.InvalidUrlException.class)
  public void normalize_invalid_incomplete() throws Exception {

    String orignalUrl = "/Something";

    normalizer.normalize(orignalUrl);
  }

}