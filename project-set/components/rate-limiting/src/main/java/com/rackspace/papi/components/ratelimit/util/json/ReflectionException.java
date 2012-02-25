package com.rackspace.papi.components.ratelimit.util.json;

/**
 *
 * @author zinic
 */
public class ReflectionException extends RuntimeException {

   public ReflectionException(String string, Throwable thrwbl) {
      super(string, thrwbl);
   }
}
