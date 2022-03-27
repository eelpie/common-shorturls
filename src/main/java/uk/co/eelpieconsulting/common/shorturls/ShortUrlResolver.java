package uk.co.eelpieconsulting.common.shorturls;

import java.net.URL;

public interface ShortUrlResolver {

    /**
     * Given a url determine if is a type this resolver can expand.
     * @param url
     * @return true if this resolver can be used to try to resolve this url
     */
    public boolean isValid(URL url);

    /**
     * Given a short url attempt to expand it.
     * @param url
     * @return The resolved full url or the unresolved short url if it is not supported by this resolver. Null if the resolution failed.
     */
    public URL resolveUrl(URL url);

}


