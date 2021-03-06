
import com.rackspace.papi.components.datastore.DistributedDatastoreFilter;
import com.rackspace.papi.components.ratelimit.RateLimitingFilter;
import com.rackspace.papi.servlet.InitParameter;
import com.rackspace.papi.filter.PowerFilter;
import com.rackspace.papi.service.context.PowerApiContextManager;
import com.rackspace.papi.jetty.JettyServerBuilder;
import com.rackspace.papi.jetty.JettyTestingContext;
import com.rackspace.papi.test.DummyServlet;

public class StandAloneRateLimitingServer extends JettyTestingContext {

   public static void main(String[] args) {
      try {
         new StandAloneRateLimitingServer(2101);
         new StandAloneRateLimitingServer(2102);
         new StandAloneRateLimitingServer(2103);
         new StandAloneRateLimitingServer(2104);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public StandAloneRateLimitingServer(int port) throws Exception {
      final JettyServerBuilder server = new JettyServerBuilder(port);
      buildServerContext(server);
      server.start();

      System.out.println("Server started");
   }

   @Override
   public final void buildServerContext(JettyServerBuilder serverBuilder) throws Exception {
      serverBuilder.addContextListener(PowerApiContextManager.class);
      serverBuilder.addContextInitParameter(InitParameter.POWER_API_CONFIG_DIR.getParameterName(), "/home/zinic/ulocal/local/etc/powerapi/rate-limiting");
      serverBuilder.addFilter(PowerFilter.class, "/*");
      serverBuilder.addFilter(DistributedDatastoreFilter.class, "/*");
      serverBuilder.addFilter(RateLimitingFilter.class, "/*");
      serverBuilder.addServlet(DummyServlet.class, "/*");
   }
}
