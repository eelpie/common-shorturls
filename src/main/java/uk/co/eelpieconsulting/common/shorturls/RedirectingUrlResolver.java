package uk.co.eelpieconsulting.common.shorturls;

public interface RedirectingUrlResolver {

    public abstract boolean isValid(String url);

    public String resolveUrl(String url);

}
