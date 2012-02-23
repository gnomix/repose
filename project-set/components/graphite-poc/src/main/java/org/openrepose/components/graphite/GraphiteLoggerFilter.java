package org.openrepose.components.graphite;

import org.slf4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

// I am poorly written and I don't care! Proof of concept, sukkas

public class GraphiteLoggerFilter implements Filter {

   private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(GraphiteLoggerFilter.class);
   private Map<String, Long> lastLoggedTimestampMap;
   private StatsTracker responseCodesPerSecond;
   private Socket graphiteSock;
   private PrintWriter graphiteWriter;

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      chain.doFilter(request, response);
      final String statusCode = String.valueOf(((HttpServletResponse) response).getStatus());

      responseCodesPerSecond.event(statusCode);
      logEvent(responseCodesPerSecond.get(statusCode));
   }

   private synchronized void logEvent(StatsEvent event) {
      final Long lastTimeStamp = lastLoggedTimestampMap.get(event.getFullName());

      if (lastTimeStamp == null || System.currentTimeMillis() - lastTimeStamp > 1000) {
         LOG.debug(event.getFullName() + " " + event.getValue() + " " + nowInSeconds());

         graphiteWriter.print(event.getFullName() + " " + event.getValue() + " " + nowInSeconds() + "\n");
         graphiteWriter.flush();

         lastLoggedTimestampMap.put(event.getFullName(), System.currentTimeMillis());
      }
   }

   private long nowInSeconds() {
      return System.currentTimeMillis() / 1000;
   }

   @Override
   public void destroy() {
   }

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
      responseCodesPerSecond = new StatsTracker("responses.status", 1000);
      lastLoggedTimestampMap = new HashMap<String, Long>();

      try {
         graphiteSock = new Socket(InetAddress.getLocalHost(), 2003);
         graphiteWriter = new PrintWriter(graphiteSock.getOutputStream());
      } catch (IOException ioe) {
         LOG.error("IO Failure: " + ioe.getMessage());
         throw new ServletException(ioe.getMessage(), ioe);
      }
   }
}
