package org.openrepose.rnxp.servlet.http.live;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.openrepose.rnxp.http.HttpMessageComponentOrder;
import org.openrepose.rnxp.http.io.control.HttpConnectionController;

/**
 *
 * @author zinic
 */
public abstract class AbstractHttpServletResponse extends AbstractUpdatableHttpMessage implements HttpServletResponse {

   public AbstractHttpServletResponse(HttpConnectionController updateController, HttpMessageComponentOrder componentOrder) {
      super(updateController, componentOrder);
   }

   @Override
   public void addCookie(Cookie cookie) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void addDateHeader(String name, long date) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void addHeader(String name, String value) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void addIntHeader(String name, int value) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public boolean containsHeader(String name) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String encodeRedirectURL(String url) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String encodeRedirectUrl(String url) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String encodeURL(String url) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String encodeUrl(String url) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String getHeader(String name) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Collection<String> getHeaderNames() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Collection<String> getHeaders(String name) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public int getStatus() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendError(int sc, String msg) throws IOException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendError(int sc) throws IOException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendRedirect(String location) throws IOException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setDateHeader(String name, long date) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setHeader(String name, String value) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setIntHeader(String name, int value) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setStatus(int sc) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setStatus(int sc, String sm) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void flushBuffer() throws IOException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public int getBufferSize() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String getCharacterEncoding() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public String getContentType() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Locale getLocale() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public PrintWriter getWriter() throws IOException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public boolean isCommitted() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void reset() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void resetBuffer() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setBufferSize(int size) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setCharacterEncoding(String charset) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setContentLength(int len) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setContentType(String type) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void setLocale(Locale loc) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
