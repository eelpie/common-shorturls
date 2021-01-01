package uk.co.eelpieconsulting.common.shorturls.resolvers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.eelpieconsulting.common.shorturls.ShortUrlResolver;

import java.util.Arrays;
import java.util.Optional;

public class CompositeUrlResolver implements ShortUrlResolver {

    private final static Logger log = LogManager.getLogger(CompositeUrlResolver.class);

    protected ShortUrlResolver[] resolvers;

    public CompositeUrlResolver(ShortUrlResolver... resolvers) {
        this.resolvers = resolvers;
    }

    public boolean isValid(String url) {
        return resolverFor(url).isPresent();
    }

    public String resolveUrl(String url) {
        return resolveUrl(url, 0);
    }

    private String resolveUrl(String url, int depth) {
        if (isValid(url) && depth <= 5) {
            String resolvedUrl = resolveSingleUrl(url);
            // If the url resolved to a new url
            // which is also resolvable then we have nested shorteners and we should recurse to resolve again
            boolean hasChanged = !url.equals(resolvedUrl);
            if (hasChanged) {
                return resolveUrl(resolvedUrl, depth + 1);
            } else {
                return resolvedUrl;
            }
        } else {
            return url;
        }
    }

    private String resolveSingleUrl(final String url) {
        return resolverFor(url).map(resolver -> {
            String resolvedUrl = resolver.resolveUrl(url);
            if (!resolvedUrl.equals(url)) {
                log.info("Redirected url '" + url + "' resolved to: " + resolvedUrl);
            }
            return resolvedUrl;
        }).orElse(url);
    }

    private Optional<ShortUrlResolver> resolverFor(String url) {
        return Arrays.stream(resolvers).filter(resolver -> resolver.isValid(url)).findFirst();
    }

}
