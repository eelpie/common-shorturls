package uk.co.eelpieconsulting.common.shorturls.resolvers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import uk.co.eelpieconsulting.common.shorturls.ShortUrlResolver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public abstract class RedirectingUrlResolver implements ShortUrlResolver {

    private final static Log log = LogFactory.getLog(RedirectingUrlResolver.class);

    private static final String LOCATION = "Location";
    private static final int HTTP_TIMEOUT = 10000;

    private final String domain;

    protected RedirectingUrlResolver(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean isValid(URL url) {
        return url != null && url.getHost().equals(domain);
    }

    @Override
    public URL resolveUrl(URL url) {
        if (url != null && isValid(url)) {
            log.info("Resolving url: " + url);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(HTTP_TIMEOUT)
                    .setConnectionRequestTimeout(HTTP_TIMEOUT)
                    .setSocketTimeout(HTTP_TIMEOUT).build();

            HttpClient client = HttpClientBuilder.create().
                    setDefaultRequestConfig(requestConfig).disableRedirectHandling().build();

            try {
                final HttpHead head = new HttpHead(url.toExternalForm());
                final HttpResponse response = client.execute(head);

                final int statusCode = response.getStatusLine().getStatusCode();
                log.debug("Response status code was: " + statusCode);

                final boolean httpResponseWasRedirect = statusCode == HttpStatus.SC_MOVED_TEMPORARILY
                        || statusCode == HttpStatus.SC_MOVED_PERMANENTLY
                        || statusCode == 308;
                if (httpResponseWasRedirect) {
                    final Header locationHeader = response.getFirstHeader(LOCATION);
                    if (locationHeader != null) {
                        String location = URLDecoder.decode(locationHeader.getValue(), "UTF-8");
                        try {
                            return new URL(location);
                        } catch (MalformedURLException e) {
                            log.warn("Returned redirection URL could not be parsed: " + location);
                            return null;
                        }
                    } else {
                        log.warn("No location header seen on response");
                        return null;
                    }

                } else {
                    log.warn("The http call did not return an expected redirect");
                    return null;
                }

            } catch (IOException | IllegalArgumentException e) {
                log.error(e);
                return null;
            }

        } else {
            log.warn("Url was is supported by this resolver; no resolution attempted: " + url);
            return url;
        }
    }

}
