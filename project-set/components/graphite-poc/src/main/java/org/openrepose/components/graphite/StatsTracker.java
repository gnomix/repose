package org.openrepose.components.graphite;

import java.util.*;

/**
 *
 * @author zinic
 */
public class StatsTracker {

   private final Map<String, List<Long>> trackingMap;
   private final String eventPrefix;
   private final long eventLifetime;

   public StatsTracker(String eventPrefix, long eventLifetime) {
      this.eventPrefix = eventPrefix;
      this.eventLifetime = eventLifetime;
      
      trackingMap = new HashMap<String, List<Long>>();
   }

   public synchronized void event(String eventId) {
      List<Long> eventList = trackingMap.get(eventId);
      
      if (eventList == null) {
         eventList = new LinkedList<Long>();
         trackingMap.put(eventId, eventList);
      }
      
      eventList.add(now());
   }
   
   public synchronized StatsEvent get(String eventId) {
      List<Long> eventList = trackingMap.get(eventId);
      
      if (eventList == null) {
         eventList = Collections.EMPTY_LIST;
      }
      
      final long now = now();
      long eventHits = 0;
      
      for (Iterator<Long> eventListIterator = eventList.iterator(); eventListIterator.hasNext();) {
         final Long eventStamp = eventListIterator.next();
         
         if (now - eventStamp > eventLifetime) {
            eventListIterator.remove();
         } else {
            eventHits++;
         }
      }
      
      return new StatsEvent(eventPrefix + "." + eventId, eventHits);
   }
   
   private long now() {
      return System.currentTimeMillis();
   }
}
