package uk.co.eelpieconsulting.common.shorturls.resolvers;

public class TinyUrlResolver extends RedirectingUrlResolver {

    public TinyUrlResolver() {
        super("http://tinyurl.com/");
    }
}
