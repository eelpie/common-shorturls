package uk.co.eelpieconsulting.common.shorturls.resolvers;

public class FeedBurnerRedirectResolver extends RedirectingUrlResolver {

    public FeedBurnerRedirectResolver() {
        super("http://feedproxy.google.com/");
    }

}
