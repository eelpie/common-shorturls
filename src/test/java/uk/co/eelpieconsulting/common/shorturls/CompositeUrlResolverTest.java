package uk.co.eelpieconsulting.common.shorturls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.co.eelpieconsulting.common.shorturls.resolvers.*;

import java.net.MalformedURLException;
import java.net.URL;

public class CompositeUrlResolverTest {

    @Test
    public void canDetectValidResolvers() throws MalformedURLException {
        CompositeUrlResolver service = new CompositeUrlResolver(new FeedBurnerRedirectResolver(), new TinyUrlResolver());

        assertTrue(service.isValid(new URL("http://tinyurl.com/123")));
        assertFalse(service.isValid(new URL("http://wellington.gen.nz")));
    }

    @Test
    public void canResolveSingleLevelShortUrl() throws MalformedURLException {
        CompositeUrlResolver service = new CompositeUrlResolver(new FeedBurnerRedirectResolver());

        URL resolvedUrl = service.resolveUrl(new URL("http://feedproxy.google.com/~r/wellynews/~3/yGwOxeMzH68/09_04_29.htm"));

        assertEquals(new URL("http://www.ccdhb.org.nz/News/2009_archive/09_04_29.htm?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed: wellynews (Search Wellington - Wellington Newslog)"),
                resolvedUrl);
    }

    @Test
    public void canResolveNestedShortUrls() throws MalformedURLException {
        CompositeUrlResolver service = new CompositeUrlResolver(new TwitterHttpsShortenerUrlResolver(), new TinyUrlHttpsResolver());

        URL resolvedUrl = service.resolveUrl(new URL("https://t.co/Gl8KhGhCa4?amp=1"));

        assertEquals(new URL("https://wellington.govt.nz/news-and-events/news-and-information/news/2020/12/volunteers-dive-to-keep-citys-harbour-clean"), resolvedUrl);
    }

}
