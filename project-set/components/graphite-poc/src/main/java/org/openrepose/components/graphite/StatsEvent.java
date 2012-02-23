package org.openrepose.components.graphite;

/**
 *
 * @author zinic
 */
public class StatsEvent {

   private final String fullName;
   private final long value;

   public StatsEvent(String fullName, long value) {
      this.fullName = fullName;
      this.value = value;
   }

   public String getFullName() {
      return fullName;
   }

   public long getValue() {
      return value;
   }
}
