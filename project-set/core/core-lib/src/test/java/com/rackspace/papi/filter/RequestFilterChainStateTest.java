package com.rackspace.papi.filter;

import com.rackspace.papi.filter.resource.ResourceMonitor;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.*;

/**
 * @author fran
 */
@RunWith(Enclosed.class)
public class RequestFilterChainStateTest {

    public static class WhenUsingPowerFilterChain {

        @Test
        public void shouldDoFilter() throws IOException, ServletException {
            List<FilterContext> filterContextList = new ArrayList<FilterContext>();
            Filter mockedFilter = mock(Filter.class);
            FilterContext mockedFilterContext = mock(FilterContext.class);
            ClassLoader mockedClassLoader = mock(ClassLoader.class);
            when(mockedFilterContext.getFilter()).thenReturn(mockedFilter);
            when(mockedFilterContext.getFilterClassLoader()).thenReturn(mockedClassLoader);
            filterContextList.add(mockedFilterContext);
            FilterChain mockedFilterChain = mock(FilterChain.class);

            PowerFilterChain powerFilterChainState = new PowerFilterChain(filterContextList, mockedFilterChain, mock(ServletContext.class), mock(ResourceMonitor.class));

            HttpServletRequest mockedServletRequest = mock(HttpServletRequest.class);
            HttpServletResponse mockedServletResponse = mock(HttpServletResponse.class);

            when(mockedServletRequest.getRequestURL()).thenReturn(new StringBuffer());

            powerFilterChainState.doFilter(mockedServletRequest, mockedServletResponse);

            powerFilterChainState.doFilter(mockedServletRequest, mockedServletResponse);
        }
    }
}
