package uk.co.eelpieconsulting.common.shorturls;

public class FeedBurnerRedirectResolver extends AbstractRedirectResolver {
    
    private static final String FEEDBURNER_LINK_URL_PREFIX = "http://feedproxy.google.com/";
    
    public boolean isValid(String url) {
        return url != null && url.startsWith(FEEDBURNER_LINK_URL_PREFIX);
    }
    
}
