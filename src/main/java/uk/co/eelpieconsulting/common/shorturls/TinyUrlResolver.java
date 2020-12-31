package uk.co.eelpieconsulting.common.shorturls;

public class TinyUrlResolver extends AbstractRedirectResolver {

    private static final String TINYURL_PREFIX = "http://tinyurl.com/";

    public boolean isValid(String url) {
        return url != null && url.startsWith(TINYURL_PREFIX);
    }

}
