package com.rackspace.papi.commons.util.logging.apache.format.stock;

import com.rackspace.papi.commons.util.logging.apache.format.FormatterLogic;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHeaderHandler extends HeaderHandler implements FormatterLogic {

   public RequestHeaderHandler(String headerName, List<String> arguments) {
      super(headerName, arguments);
   }

   @Override
   public String handle(HttpServletRequest request, HttpServletResponse response) {
      return getValues(request.getHeaders(getHeaderName()));
   }
   
}
