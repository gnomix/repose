package com.rackspace.papi.commons.util;

import com.rackspace.papi.commons.util.SetUtilities;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;

/**
 * @author fran
 */
@RunWith(Enclosed.class)
public class SetUtilitiesTest {
    public static class WhenPerformingNullSafeEquals {
        @Test
        public void shouldReturnFalseIfFirstSetIsNull() {
            Set<String> one = null;
            Set<String> two = new HashSet();
            two.add("abc");

            assertFalse(SetUtilities.nullSafeEquals(one, two));
        }

        @Test
        public void shouldReturnFalseIfSecondSetIsNull() {
            Set<String> one = new HashSet();
            one.add("abc");
            Set<String> two = null;

            assertFalse(SetUtilities.nullSafeEquals(one, two));
        }

        @Test
        public void shouldReturnFalseIfNonNullSetsAreDifferent() {
            Set<String> one = new HashSet();
            one.add("abc");
            Set<String> two = new HashSet();
            two.add("def");

            assertFalse(SetUtilities.nullSafeEquals(one, two));
        }

        @Test
        public void shouldReturnTrueIfBothSetsAreNull() {
            Set<String> one = null;
            Set<String> two = null;

            assertTrue(SetUtilities.nullSafeEquals(one, two));
        }

        @Test
        public void shouldReturnTrueIfNonNullSetsAreSame() {
            Set<String> one = new HashSet();
            one.add("abc");
            Set<String> two = new HashSet();
            two.add("abc");

            assertTrue(SetUtilities.nullSafeEquals(one, two));
        }

        @Test
        public void shouldReturnFalseIfNonNullSetsAreSameButDifferentCase() {
            Set<String> one = new HashSet();
            one.add("abc");
            Set<String> two = new HashSet();
            two.add("AbC");

            assertFalse(SetUtilities.nullSafeEquals(one, two));
        }
    }
}
