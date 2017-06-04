package my.test.shortener.shortening;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * tests
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class AlphabetHashingTest {

  private AlphabetHashing hashing;

  @Before
  public void setUp() throws Exception {

    hashing = new AlphabetHashing();

  }

  @Test
  public void hash() throws Exception {

    int length = 5;

    String hash = hashing.hash("http://google.com", length);

    assertThat(hash.length(), is(equalTo(length)));
    assertThat(StringUtils.hasText(hash), is(true));
  }

  @Test
  public void hash_length() throws Exception {

    int length = 10;

    String hash = hashing.hash("http://google.com", length);

    assertThat(hash.length(), is(equalTo(length)));
    assertThat(StringUtils.hasText(hash), is(true));
  }

  @Test
  public void hash_twice() throws Exception {

    String hash = hashing.hash("http://google.com", 5);
    String hash2 = hashing.hash("http://google.com", 5);

    assertThat(hash.length(), is(equalTo(5)));
    assertThat(StringUtils.hasText(hash), is(true));
    assertThat(hash, is(equalTo(hash2)));
  }

  @Test
  public void hash_trim() throws Exception {

    String hash = hashing.hash("http://google.com", 5);
    String hash2 = hashing.hash("   http://google.com             ", 5);

    assertThat(hash.length(), is(equalTo(5)));
    assertThat(StringUtils.hasText(hash), is(true));
    assertThat(hash, is(equalTo(hash2)));
  }

  @Test
  public void hash_basic_different() throws Exception {

    String hash = hashing.hash("http://google.com", 5);
    String hash2 = hashing.hash("http://google.com/", 5);

    assertThat(hash.length(), is(equalTo(5)));
    assertThat(StringUtils.hasText(hash), is(true));
    assertThat(hash, is(not(equalTo(hash2))));
  }

  @Test(expected = Hashing.EmptyValueException.class)
  public void hash_empty() throws Exception {

    String hash = hashing.hash("", 5);
  }


  @Test(expected = Hashing.EmptyValueException.class)
  public void hash_null() throws Exception {

    String hash = hashing.hash(null, 5);
  }

}