package uk.co.eelpieconsulting.common.shorturls.resolvers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.co.eelpieconsulting.common.shorturls.ShortUrlResolver;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

public class CompositeUrlResolver implements ShortUrlResolver {

    private final static Log log = LogFactory.getLog(CompositeUrlResolver.class);

    protected ShortUrlResolver[] resolvers;

    public CompositeUrlResolver(ShortUrlResolver... resolvers) {
        this.resolvers = resolvers;
    }

    @Override
    public boolean isValid(URL url) {
        return resolverFor(url).isPresent();
    }

    @Override
    public URL resolveUrl(URL url) {
        return resolveUrl(url, 0);
    }

    private URL resolveUrl(URL url, int depth) {
        if (isValid(url) && depth <= 5) {
            URL resolvedUrl = resolveSingleUrl(url);
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

    private URL resolveSingleUrl(final URL url) {
        return resolverFor(url).map(resolver -> {
            URL resolvedUrl = resolver.resolveUrl(url);
            if (!resolvedUrl.equals(url)) {
                log.info("Redirected url '" + url + "' resolved to: " + resolvedUrl);
            }
            return resolvedUrl;
        }).orElse(url);
    }

    private Optional<ShortUrlResolver> resolverFor(URL url) {
        return Arrays.stream(resolvers).filter(resolver -> resolver.isValid(url)).findFirst();
    }

}
