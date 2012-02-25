package com.rackspace.papi.components.ratelimit.util.json;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 *
 * Playing with my own version of auto-serializing JSON writer for JAXB
 * annotated entities
 *
 * This is just an experiment with Jackson at a lower API in order to acquire
 * more fine-grained control of the JSON that is produced during the
 * serialization process.
 *
 * This is far from complete and has no tests. Because of this I've marked the
 * class deprecated.
 *
 * @deprecated
 * @author zinic
 */

@RunWith(Enclosed.class)
public class JaxbObjectJsonWriterTest {

   public static class WhenWritingStuff {

      @Test
      public void shouldDoThings() {
      }
   }
}
