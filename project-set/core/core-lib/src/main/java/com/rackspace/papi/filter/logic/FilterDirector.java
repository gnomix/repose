package com.rackspace.papi.filter.logic;

import com.rackspace.papi.commons.util.http.HttpStatusCode;
import com.rackspace.papi.commons.util.servlet.http.MutableHttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jhopper
 */
public interface FilterDirector {

    void setRequestUri(String newUri);

    void setRequestUrl(StringBuffer newUrl);

    String getRequestUri();

    StringBuffer getRequestUrl();

    HeaderManager requestHeaderManager();

    HeaderManager responseHeaderManager();

    FilterAction getFilterAction();

    HttpStatusCode getResponseStatus();

    int getResponseStatusCode();

    void setFilterAction(FilterAction action);

    void setResponseStatus(HttpStatusCode delegatedStatus);

    public void setResponseStatusCode(int status);
    
    String getResponseMessageBody();

    byte[] getResponseMessageBodyBytes();

    PrintWriter getResponseWriter();

    OutputStream getResponseOutputStream();

    void applyTo(MutableHttpServletRequest request);

    void applyTo(HttpServletResponse response) throws IOException;
}
