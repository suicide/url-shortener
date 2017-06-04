package my.test.shortener.shortening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Uses Spring UriComponents to normalize URLs
 *
 * @author Patrick Sy (patrick.sy@get-it.us)
 */
public class UriComponentsUrlNormalizer implements UrlNormalizer {

  private static final Logger LOGGER = LoggerFactory.getLogger(UriComponentsUrlNormalizer.class);

  private static final String DEFAULT_SCHEME = "http";

  private static final List<String> ALLOWED_SCHEMES = Arrays.asList(DEFAULT_SCHEME, "https");

  @Override
  public String normalize(String url) {

    String trimmed = StringUtils.trimWhitespace(url);

    if (!StringUtils.hasText(trimmed)) {
      throw new InvalidUrlException(String.format("Given url %s is empty", url));
    }

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(trimmed);

    UriComponents original = builder.build();

    // scheme
    Optional<String> scheme = Optional.ofNullable(original.getScheme());
    if (!scheme.isPresent()) {
      builder.scheme(DEFAULT_SCHEME);
    } else {
      scheme.filter(ALLOWED_SCHEMES::contains)
        .orElseThrow(
          () -> new InvalidUrlException(
            String.format("Url %s does not contain a allowed scheme %s", url, ALLOWED_SCHEMES)));
    }

    // host
    Optional<String> host = Optional.ofNullable(original.getHost());
    if (!host.isPresent()) {
      List<String> pathSegments = original.getPathSegments();
      if (!pathSegments.isEmpty() && !original.getPath().startsWith("/")) {
        String newHost = pathSegments.get(0).toLowerCase();
        builder.host(newHost);
        builder.replacePath(original.getPath().substring(newHost.length()));
      } else {
        throw new InvalidUrlException(String.format("No host found in url %s", url));
      }
    } else {
      host.map(String::toLowerCase).ifPresent(builder::host);
    }


    return builder.toUriString();
  }
}
