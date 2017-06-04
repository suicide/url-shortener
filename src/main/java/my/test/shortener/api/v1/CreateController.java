package my.test.shortener.api.v1;

import my.test.shortener.shortening.Hashing;
import my.test.shortener.shortening.ShortenerService;
import my.test.shortener.shortening.UrlNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Creates a short url from a given url
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
@Controller
@RequestMapping("/api/v1")
public class CreateController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateController.class);

  private final ShortenerService shortenerService;

  private final String baseUri;

  public CreateController(ShortenerService shortenerService, String baseUri) {
    this.shortenerService = shortenerService;
    this.baseUri = baseUri;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> handleCreate(@RequestBody String url) {

    LOGGER.debug("Handling url {}", url);

    try {
      String idHash = shortenerService.shorten(url);

      String redirectUri = baseUri + idHash;

      HttpHeaders headers = new HttpHeaders();
      headers.add("Location", redirectUri);

      return new ResponseEntity<>(headers, HttpStatus.CREATED);

    } catch (ShortenerService.ShortenerServiceException
      | UrlNormalizer.InvalidUrlException
      | Hashing.EmptyValueException e) {
      LOGGER.debug("The given url {} could not be processed", url, e);

      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

}
