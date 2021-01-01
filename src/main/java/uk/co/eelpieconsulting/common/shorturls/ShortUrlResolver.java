package uk.co.eelpieconsulting.common.shorturls;

public interface ShortUrlResolver {

    /**
     * Given a url
     * @param url
     * @return true if this resolver can be used to try to resolve this url
     */
    public boolean isValid(String url);

    /**
     * Given a url
     * @param url
     * @return The resolved full url or the unresolved short url if it could not be resolved.
     */
    public String resolveUrl(String url);

}


