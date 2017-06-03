package my.test.shortener.api.v1;

import my.test.shortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Short URL lookup
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Controller
@RequestMapping("/api/v1")
public class LookupController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LookupController.class);

  /**
   * repository containing the long urls
   */
  private final UrlRepository urlRepository;

  public LookupController(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  @RequestMapping(value = "/{hash:[a-zA-Z0-9]+}", method = RequestMethod.GET)
  public ResponseEntity<Void> handleGet(@PathVariable("hash") String hash) {

    LOGGER.debug("Handling hash {}", hash);

    return urlRepository.getLongUrl(hash)
      .map(url -> {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", url);

        return new ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY);
      }).orElseGet(() -> new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

  }

}
