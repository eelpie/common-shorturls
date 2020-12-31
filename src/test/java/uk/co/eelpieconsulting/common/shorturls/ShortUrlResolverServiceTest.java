package uk.co.eelpieconsulting.common.shorturls;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import uk.co.eelpieconsulting.common.shorturls.resolvers.FeedBurnerRedirectResolver;

public class ShortUrlResolverServiceTest {

    @Test
    public void canResolveSingleLevelShortUrl() {
        ShortUrlResolverService service = new ShortUrlResolverService(new FeedBurnerRedirectResolver());

        assertEquals("http://www.ccdhb.org.nz/News/2009_archive/09_04_29.htm?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed: wellynews (Search Wellington - Wellington Newslog)",
                service.resolveUrl("http://feedproxy.google.com/~r/wellynews/~3/yGwOxeMzH68/09_04_29.htm"));
    }

}
