package uk.co.eelpieconsulting.common.shorturls.resolvers;

public class TwitterHttpsShortenerUrlResolver extends RedirectingUrlResolver {

    public TwitterHttpsShortenerUrlResolver() {
        super("https://t.co/");
    }

}
