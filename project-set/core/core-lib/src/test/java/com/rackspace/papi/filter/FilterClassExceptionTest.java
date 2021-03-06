package com.rackspace.papi.filter;

import com.rackspace.papi.filter.FilterClassException;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author fran
 */
@RunWith(Enclosed.class)
public class FilterClassExceptionTest {
    public static class WhenUsingFilterClassException {
        @Test
        public void shouldProcessCustomMessage() {
            String expectedExceptionMessage = "Oops!  Something unexpected happened.";
            FilterClassException filterClassException = new FilterClassException(expectedExceptionMessage, new Throwable("unexpected"));

            assertEquals(expectedExceptionMessage, filterClassException.getMessage());
        }
    }
}
