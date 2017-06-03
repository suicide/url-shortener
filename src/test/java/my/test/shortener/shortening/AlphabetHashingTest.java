package my.test.shortener.shortening;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * TODO: Comment
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

    String hash = hashing.hash("http://google.com", 5);

    assertThat(hash.length(), is(equalTo(5)));

  }

}