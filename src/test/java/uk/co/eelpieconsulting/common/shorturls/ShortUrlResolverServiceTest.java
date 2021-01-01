package uk.co.eelpieconsulting.common.shorturls;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import uk.co.eelpieconsulting.common.shorturls.resolvers.*;

public class ShortUrlResolverServiceTest {

    @Test
    public void canResolveSingleLevelShortUrl() {
        ShortUrlResolverService service = new ShortUrlResolverService(new FeedBurnerRedirectResolver());

        String resolvedUrl = service.resolveUrl("http://feedproxy.google.com/~r/wellynews/~3/yGwOxeMzH68/09_04_29.htm");

        assertEquals("http://www.ccdhb.org.nz/News/2009_archive/09_04_29.htm?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed: wellynews (Search Wellington - Wellington Newslog)",
                resolvedUrl);
    }

    @Test
    public void canResolveNestedShortUrls() {
        ShortUrlResolverService service = new ShortUrlResolverService(new TwitterHttpsShortenerUrlResolver(), new TinyUrlHttpsResolver());

        String resolvedUrl = service.resolveUrl("https://t.co/Gl8KhGhCa4?amp=1");

        assertEquals("https://wellington.govt.nz/news-and-events/news-and-information/news/2020/12/volunteers-dive-to-keep-citys-harbour-clean", resolvedUrl);
    }

}
