package com.rackspace.papi.commons.util.http.header;

import com.rackspace.papi.commons.util.StringUtilities;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author zinic
 */
public class HeaderValueImpl implements HeaderValue {

   public static final String QUALITY_FACTOR_PARAM_NAME = "q";
   public static final double DEFAULT_QUALITY = 1.0;
   private final Map<String, String> parameters;
   private final String value;
   private double parsedQualityFactor;

   private static double getQualityFactor(Map<String, String> parameters) throws MalformedHeaderValueException {
      double qualityFactor = 1;

      final String qualityFactorString = parameters.get(QUALITY_FACTOR_PARAM_NAME);

      if (StringUtilities.isNotBlank(qualityFactorString)) {
         try {
            qualityFactor = Double.parseDouble(qualityFactorString);
         } catch (NumberFormatException nfe) {
            throw new MalformedHeaderValueException("Quality factor is not a valid double", nfe);
         }
      }

      return qualityFactor;
   }

   private static Map<String, String> qualityFactorToParameterMap(double qualityFactor) {
      final Map<String, String> parameters = new HashMap<String, String>();
      if (qualityFactor != DEFAULT_QUALITY) {
         parameters.put(QUALITY_FACTOR_PARAM_NAME, String.valueOf(qualityFactor));
      }

      return parameters;
   }

   /**
    * Constructor that sets the header value's quality factor directly
    *
    * @param value
    * @param qualityFactor
    */
   public HeaderValueImpl(String value, double qualityFactor) {
      this.value = value;
      this.parameters = qualityFactorToParameterMap(qualityFactor);
      this.parsedQualityFactor = qualityFactor;
   }

   /**
    * This constructor copies the parameter map into the header value parameter map.
    *
    * @param value
    * @param parameters
    */
   public HeaderValueImpl(String value, Map<String, String> parameters) throws MalformedHeaderValueException {
      this.parsedQualityFactor = getQualityFactor(parameters);
      this.parameters = new HashMap(parameters);
      this.value = value;
   }

   @Override
   public double getQualityFactor() {
      return parsedQualityFactor;
   }

   @Override
   public String getValue() {
      return value;
   }

   @Override
   public Map<String, String> getParameters() {
      return Collections.unmodifiableMap(parameters);
   }

   public boolean hasQualityFactor() {
      return parameters.containsKey(QUALITY_FACTOR_PARAM_NAME);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }

      if (getClass() != obj.getClass()) {
         return false;
      }

      final HeaderValueImpl other = (HeaderValueImpl) obj;

      return compareTo(other) == 0;
   }

   @Override
   public int hashCode() {
      int hash = 7;

      hash = 67 * hash + (this.parameters != null ? this.parameters.hashCode() : 0);
      hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);

      return hash;
   }

   @Override
   public int compareTo(HeaderValue that) {
      int comparasionValue = 1;

      if (that != null) {
         if (this.getQualityFactor() != that.getQualityFactor()) {
            comparasionValue = this.getQualityFactor() > that.getQualityFactor() ? 1 : -1;
         } else {
            comparasionValue = compareHeaderValues(this.getValue(), that.getValue());
         }
      }

      return comparasionValue;
   }

   private int compareHeaderValues(String first, String second) {
      int comparasionValue = -1;

      if (first != null) {
         if (second == null) {
            comparasionValue = 1;
         } else {
            comparasionValue = first.compareTo(second);
         }
      } else if (second == null) {
         comparasionValue = 0;
      }

      return comparasionValue;
   }

   @Override
   public String toString() {
      final StringBuilder builder = new StringBuilder(value);

      if (!parameters.isEmpty()) {
         builder.append(";");

         final Iterator<Entry<String, String>> parameterIterator = parameters.entrySet().iterator();
         boolean hasNext = parameterIterator.hasNext();

         while (hasNext) {
            final Entry<String, String> nextParameter = parameterIterator.next();
            builder.append(nextParameter.getKey()).append("=").append(nextParameter.getValue());

            if (hasNext = parameterIterator.hasNext()) {
               builder.append(";");
            }
         }
      }

      return builder.toString();
   }
}
