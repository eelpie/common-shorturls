package uk.co.eelpieconsulting.common.shorturls;

public class BitlyUrlResolver extends AbstractRedirectResolver {

    private static final String BITLY_URL_PREFIX = "http://bit.ly/";

    public boolean isValid(String url) {
        return url != null && url.startsWith(BITLY_URL_PREFIX);
    }

}
