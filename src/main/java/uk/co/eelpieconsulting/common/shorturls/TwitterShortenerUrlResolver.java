package uk.co.eelpieconsulting.common.shorturls;

public class TwitterShortenerUrlResolver extends AbstractRedirectResolver {

    public TwitterShortenerUrlResolver() {
        super("http://t.co/");
    }

}
