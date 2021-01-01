package uk.co.eelpieconsulting.common.shorturls.resolvers;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.eelpieconsulting.common.shorturls.ShortUrlResolver;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

public abstract class RedirectingUrlResolver implements ShortUrlResolver {

    private final static Logger log = LogManager.getLogger(RedirectingUrlResolver.class);

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

            final HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter("http.socket.timeout", HTTP_TIMEOUT);
            client.getParams().setParameter("http.protocol.handle-redirects", false);

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
                        return new URL(location);   // TODO exceptions
                    } else {
                        log.warn("No location header seen on response");
                    }

                } else {
                    log.warn("The http call did not return an expected redirect");
                }

            } catch (IOException | IllegalArgumentException e) {
                log.error(e);
            }

        } else {
            log.warn("Url was invalid: " + null);
        }
        return null;
    }

}
