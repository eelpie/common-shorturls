package uk.co.eelpieconsulting.common.shorturls;

public class TwitterShortenerUrlResolver extends AbstractRedirectResolver {
    
	private static final String TWITTER_SHORTENER_PREFIX = "http://t.co/";
    
    public boolean isValid(String url) {
        return url != null && url.startsWith(TWITTER_SHORTENER_PREFIX);
    }
    
}
