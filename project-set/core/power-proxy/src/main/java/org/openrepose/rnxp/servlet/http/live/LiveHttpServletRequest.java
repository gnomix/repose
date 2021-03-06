package org.openrepose.rnxp.servlet.http.live;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletInputStream;
import org.openrepose.rnxp.decoder.partial.HttpMessagePartial;
import org.openrepose.rnxp.decoder.partial.impl.HeaderPartial;
import org.openrepose.rnxp.decoder.partial.impl.HttpVersionPartial;
import org.openrepose.rnxp.http.HttpMessageComponent;
import org.openrepose.rnxp.http.HttpMessageComponentOrder;
import org.openrepose.rnxp.decoder.partial.impl.RequestMethodPartial;
import org.openrepose.rnxp.decoder.partial.impl.RequestUriPartial;
import org.openrepose.rnxp.http.HttpMethod;
import org.openrepose.rnxp.http.io.control.HttpConnectionController;

/**
 *
 * @author zinic
 */
public class LiveHttpServletRequest extends AbstractHttpServletRequest implements UpdatableHttpServletRequest {

   private final Map<String, List<String>> headerMap;
   private final StringBuffer requestUrl;
   private HttpMethod requestMethod;
   private ServletInputStream servletInputSTream;
   private String requestUri, httpVersion, queryString;

   public LiveHttpServletRequest(HttpConnectionController updateController) {
      super(updateController, HttpMessageComponentOrder.requestOrderInstance());

      headerMap = new HashMap<String, List<String>>();
      requestUrl = new StringBuffer("http://localhost:8080");
      queryString = null;
   }

   @Override
   public ServletInputStream getInputStream() throws IOException {
      if (servletInputSTream == null) {
         servletInputSTream = new org.openrepose.rnxp.servlet.http.ServletInputStream(getPushInputStream());
      }

      return servletInputSTream;
   }

   @Override
   public void mergeWithPartial(HttpMessagePartial partial) {
      switch (partial.getHttpMessageComponent()) {
         case REQUEST_METHOD:
            requestMethod = ((RequestMethodPartial) partial).getHttpMethod();
            break;

         case REQUEST_URI:
            final String[] requestUriParts = ((RequestUriPartial) partial).getRequestUri().split("\\?", 2);

            requestUri = requestUriParts[0];

            if (requestUriParts.length == 2) {
               queryString = requestUriParts[1];
            }

            break;

         case HTTP_VERSION:
            httpVersion = ((HttpVersionPartial) partial).getHttpVersion();
            break;

         case HEADER:
            addHeader(((HeaderPartial) partial).getHeaderKey(), ((HeaderPartial) partial).getHeaderValue());
            break;

         default:
      }
   }

   @Override
   public String getQueryString() {
      return queryString;
   }

   @Override
   public String getProtocol() {
      return httpVersion;
   }

   @Override
   public String getMethod() {
      loadComponent(HttpMessageComponent.REQUEST_METHOD);

      return requestMethod.name();
   }

   @Override
   public String getRequestURI() {
      loadComponent(HttpMessageComponent.REQUEST_URI);

      return requestUri;
   }

   @Override
   public StringBuffer getRequestURL() {
      loadComponent(HttpMessageComponent.REQUEST_URI);

      return requestUrl;
   }

   @Override
   public String getHeader(String name) {
      loadComponent(HttpMessageComponent.HEADER);

      final List<String> headerValues = headerMap.get(name);
      return headerValues != null && headerValues.size() > 0 ? headerValues.get(0) : null;
   }

   @Override
   public Enumeration<String> getHeaderNames() {
      loadComponent(HttpMessageComponent.HEADER);

      return Collections.enumeration(headerMap.keySet());
   }

   @Override
   public Enumeration<String> getHeaders(String name) {
      loadComponent(HttpMessageComponent.HEADER);

      final List<String> headerValues = headerMap.get(name);
      return headerValues != null && headerValues.size() > 0 ? Collections.enumeration(headerValues) : null;
   }

   private List<String> newHeaderList(String headerKey) {
      final List<String> newList = new LinkedList<String>();
      headerMap.put(headerKey, newList);

      return newList;
   }

   private List<String> getHeaderList(String headerKey) {
      final List<String> list = headerMap.get(headerKey);

      return list != null ? list : newHeaderList(headerKey);
   }

   public void addHeader(String headerKey, String... values) {
      final List<String> headerList = getHeaderList(headerKey);

      headerList.addAll(Arrays.asList(values));
   }

   public void putHeader(String headerKey, String... values) {
      final List<String> headerList = getHeaderList(headerKey);
      headerList.clear();

      headerList.addAll(Arrays.asList(values));
   }
}
