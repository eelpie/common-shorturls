package uk.co.eelpieconsulting.common.shorturls.resolvers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.eelpieconsulting.common.shorturls.ShortUrlResolver;

public class CompositeUrlResolver implements ShortUrlResolver {

    private final static Logger log = LogManager.getLogger(CompositeUrlResolver.class);

    protected RedirectingUrlResolver[] redirectResolvers;

    public CompositeUrlResolver(RedirectingUrlResolver... redirectResolvers) {
        this.redirectResolvers = redirectResolvers;
    }

    public boolean isValid(String url) {
        for (RedirectingUrlResolver resolver : redirectResolvers) {
            boolean valid = resolver.isValid(url);
            if (valid) {
                return true;
            }
        }
        return false;
    }

    public String resolveUrl(String url) {
        return fullyResolveUrl(url, 0);
    }

    private String fullyResolveUrl(String url, int depth) {
        if (isValid(url) && depth <= 5) {
            String resolvedUrl = resolveSingleUrl(url);
            // If the url resolved to a new url
            // which is also resolvable then we have nested shorteners and we should recurse to resolve again
            boolean hasChanged = !url.equals(resolvedUrl);
            if (hasChanged) {
                return fullyResolveUrl(resolvedUrl, depth + 1);
            } else {
                return resolvedUrl;
            }
        } else {
            return url;
        }
    }

    protected String resolveSingleUrl(String url) {
        for (RedirectingUrlResolver resolver : redirectResolvers) {
            if (resolver.isValid(url)) {
                String resolvedUrl = resolver.resolveUrl(url);
                if (resolvedUrl != null) {
                    log.info("Redirected url '" + url + "' resolved to: " + resolvedUrl);
                    url = resolvedUrl;
                } else {
                    log.warn("Failed to resolve redirected url: " + url);
                }
            }
        }
        return url;
    }

}
