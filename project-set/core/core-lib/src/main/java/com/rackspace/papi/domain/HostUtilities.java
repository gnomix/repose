package com.rackspace.papi.domain;

import com.rackspace.papi.model.Host;
import java.net.MalformedURLException;
import java.net.URL;

public class HostUtilities {
    private static final String HTTP_PREFIX = "http";

    public static String asUrl(Host host) throws MalformedURLException {
        return new URL(HTTP_PREFIX, host.getHostname(), host.getServicePort(), "").toExternalForm();
    }
    
    public static String asUrl(Host host, String uri) throws MalformedURLException {
        return new URL(HTTP_PREFIX, host.getHostname(), host.getServicePort(), uri).toExternalForm();
    }
}
