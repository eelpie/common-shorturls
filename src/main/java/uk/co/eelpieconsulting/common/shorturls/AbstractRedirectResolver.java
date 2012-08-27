package uk.co.eelpieconsulting.common.shorturls;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

public abstract class AbstractRedirectResolver implements RedirectingUrlResolver {
    
	private final static Logger log = Logger.getLogger(AbstractRedirectResolver.class);        
    
	private static final String LOCATION = "Location";
    private static final int HTTP_TIMEOUT = 10000;
    
    public abstract boolean isValid(String url);
    
    public String resolveUrl(String url) {
        if (url != null && isValid(url)) {
            log.info("Resolving url: " + url);
           
            final HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter("http.socket.timeout", new Integer(HTTP_TIMEOUT));    
            client.getParams().setParameter("http.protocol.handle-redirects", false);    
            
            try {
            	final HttpHead head = new HttpHead(url);
            	final HttpResponse response = client.execute(head);
                
                final int statusCode = response.getStatusLine().getStatusCode();
                log.debug("Response status code was: " + statusCode);
                
				final boolean httpResponseWasRedirect = statusCode == HttpStatus.SC_MOVED_TEMPORARILY || statusCode == HttpStatus.SC_MOVED_PERMANENTLY;
                if (httpResponseWasRedirect) {                	
                    final Header locationHeader = response.getFirstHeader(LOCATION);
                    if (locationHeader != null) {
                        return URLDecoder.decode(locationHeader.getValue(), "UTF-8");
                    } else {
                    	log.warn("No location header seen on response");
                    }
                    
                } else {
                    log.warn("The http call did not return an expected redirect");
                }              
         
            } catch (IOException e) {
                log.error(e);
            } catch (IllegalArgumentException e) {
            	log.error(e);
            }
          
        } else {
            log.warn("Url was invalid: " + null);
        }
        return null;
    }

}
