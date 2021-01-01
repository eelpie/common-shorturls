package uk.co.eelpieconsulting.common.shorturls;

import java.net.URL;

public interface ShortUrlResolver {

    /**
     * Given a url
     * @param url
     * @return true if this resolver can be used to try to resolve this url
     */
    public boolean isValid(URL url);

    /**
     * Given a url
     * @param url
     * @return The resolved full url or the unresolved short url if it could not be resolved.
     */
    public URL resolveUrl(URL url);

}


