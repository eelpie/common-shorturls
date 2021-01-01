package uk.co.eelpieconsulting.common.shorturls.resolvers;

public class TinyUrlHttpsResolver extends RedirectingUrlResolver {

    public TinyUrlHttpsResolver() {
        super("https://tinyurl.com/");
    }
}
