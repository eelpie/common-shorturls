package uk.co.eelpieconsulting.common.shorturls;

import uk.co.eelpieconsulting.common.shorturls.FeedBurnerRedirectResolver;
import uk.co.eelpieconsulting.common.shorturls.RedirectingUrlResolver;
import junit.framework.TestCase;

public class FeedBurnerRedirectResolverServiceTest extends TestCase {

    public void testShouldDetectFeedburnerProxyUrls() throws Exception {
        final String feedburnerProxyUrl = "http://feedproxy.google.com/~r/wellynews/~3/yGwOxeMzH68/09_04_29.htm";
        RedirectingUrlResolver resolver = new FeedBurnerRedirectResolver();
        assertTrue(resolver.isValid(feedburnerProxyUrl));
    }

}
