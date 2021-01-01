package uk.co.eelpieconsulting.common.shorturls;

import uk.co.eelpieconsulting.common.shorturls.resolvers.FeedBurnerRedirectResolver;
import junit.framework.TestCase;
import uk.co.eelpieconsulting.common.shorturls.resolvers.RedirectingUrlResolver;

import java.net.URL;

public class FeedBurnerRedirectResolverServiceTest extends TestCase {

    public void testShouldDetectFeedburnerProxyUrls() throws Exception {
        final URL feedburnerProxyUrl = new URL("http://feedproxy.google.com/~r/wellynews/~3/yGwOxeMzH68/09_04_29.htm");

        ShortUrlResolver resolver = new FeedBurnerRedirectResolver();

        assertTrue(resolver.isValid(feedburnerProxyUrl));
    }

}
