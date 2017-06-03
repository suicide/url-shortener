package my.test.shortener.api.v1;

import my.test.shortener.shortening.ShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  public CreateController(ShortenerService shortenerService) {
    this.shortenerService = shortenerService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> handleCreate(@RequestBody String url) {

    // TODO handle application/x-www-form-urlencoded by decoding?

    LOGGER.debug("Handling url {}", url);

    try {
      String idHash = shortenerService.shorten(url);
      // TODO make URL
      return new ResponseEntity<>(idHash, HttpStatus.CREATED);

    } catch (ShortenerService.InvalidUrlException e) {
      LOGGER.debug("The given url {} could not be processed", url, e);

      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

}
