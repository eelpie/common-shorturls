package uk.co.eelpieconsulting.common.shorturls.resolvers;

public class TwitterShortenerUrlResolver extends RedirectingUrlResolver {

    public TwitterShortenerUrlResolver() {
        super("t.co");
    }

}
