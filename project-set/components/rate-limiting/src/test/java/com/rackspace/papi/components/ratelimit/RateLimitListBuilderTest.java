package com.rackspace.papi.components.ratelimit;

import com.rackspace.papi.components.limits.schema.HttpMethod;
import com.rackspace.papi.components.limits.schema.Limits;
import com.rackspace.papi.components.limits.schema.RateLimitList;
import com.rackspace.papi.components.ratelimit.cache.CachedRateLimit;
import com.rackspace.papi.components.ratelimit.config.ConfiguredLimitGroup;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 *
 * @author zinic
 */
@RunWith(Enclosed.class)
public class RateLimitListBuilderTest extends RateLimitTestContext {


    public static class WhenBuildingLiveLimits {

        private Map<String, CachedRateLimit> cacheMap;
        private ConfiguredLimitGroup configuredLimitGroup;

        @Before
        public void standUp() {
            cacheMap = new HashMap<String, CachedRateLimit>();
            configuredLimitGroup = new ConfiguredLimitGroup();

            configuredLimitGroup.setDefault(Boolean.TRUE);
            configuredLimitGroup.setId("configured-limit-group");
            configuredLimitGroup.getGroups().add("user");

            cacheMap.put(SIMPLE_URI, newCachedRateLimitFor(SIMPLE_URI, SIMPLE_URI_REGEX, HttpMethod.GET, HttpMethod.PUT));

            configuredLimitGroup.getLimit().add(newLimitFor(SIMPLE_URI, SIMPLE_URI_REGEX, HttpMethod.GET));
            configuredLimitGroup.getLimit().add(newLimitFor(SIMPLE_URI, SIMPLE_URI_REGEX, HttpMethod.PUT));
            configuredLimitGroup.getLimit().add(newLimitFor(SIMPLE_URI, SIMPLE_URI_REGEX, HttpMethod.DELETE));
            configuredLimitGroup.getLimit().add(newLimitFor(SIMPLE_URI, SIMPLE_URI_REGEX, HttpMethod.POST));

            cacheMap.put(COMPLEX_URI_REGEX, newCachedRateLimitFor(COMPLEX_URI, COMPLEX_URI_REGEX, HttpMethod.GET, HttpMethod.PUT));

            configuredLimitGroup.getLimit().add(newLimitFor(COMPLEX_URI, COMPLEX_URI_REGEX, HttpMethod.GET));
            configuredLimitGroup.getLimit().add(newLimitFor(COMPLEX_URI, COMPLEX_URI_REGEX, HttpMethod.DELETE));
            configuredLimitGroup.getLimit().add(newLimitFor(COMPLEX_URI, COMPLEX_URI_REGEX, HttpMethod.PUT));
            configuredLimitGroup.getLimit().add(newLimitFor(COMPLEX_URI, COMPLEX_URI_REGEX, HttpMethod.POST));
        }

        @Test
        public void shouldConstructLiveLimits() {
            final RateLimitList rll = new RateLimitListBuilder(cacheMap, configuredLimitGroup).toRateLimitList();

            final Limits limits = new Limits();
            limits.setRates(rll);

            assertEquals(2, rll.getRate().size());
        }

        @Test
        public void shouldMarshallContentsCorrectly() {
            final RateLimitList rll = new RateLimitListBuilder(cacheMap, configuredLimitGroup).toRateLimitList();

            final Limits limits = new Limits();
            limits.setRates(rll);

            System.out.println(ENTITY_TRANSFORMER.entityAsJson(limits));
            System.out.println(ENTITY_TRANSFORMER.entityAsXml(limits));
        }
    }
}
