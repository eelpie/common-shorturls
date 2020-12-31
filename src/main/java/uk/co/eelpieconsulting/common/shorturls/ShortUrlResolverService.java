package uk.co.eelpieconsulting.common.shorturls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.eelpieconsulting.common.shorturls.resolvers.RedirectingUrlResolver;

public class ShortUrlResolverService {

    private final static Logger log = LogManager.getLogger(ShortUrlResolverService.class);

    protected RedirectingUrlResolver[] redirectResolvers;

    public ShortUrlResolverService(RedirectingUrlResolver... redirectResolvers) {
        this.redirectResolvers = redirectResolvers;
    }

    public String resolveUrl(String url) {
        return fullyResolveUrl(url, 0);
    }

    private String fullyResolveUrl(String url, int depth) {
        depth = depth + 1;
        while (isResolvable(url) && depth < 5) {
            String resolvedUrl = resolveSingleUrl(url);
            if (!resolvedUrl.equals(url)) {
                return fullyResolveUrl(resolvedUrl, depth);
            }
            return resolvedUrl;
        }
        return url;
    }

    protected boolean isResolvable(String url) {
        for (RedirectingUrlResolver resolver : redirectResolvers) {
            if (resolver.isValid(url)) {
                return true;
            }
        }
        return false;
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
