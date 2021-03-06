package com.rackspace.papi.components.clientauth;

import com.rackspace.papi.auth.AuthModule;
import com.rackspace.papi.commons.config.manager.UpdateListener;

import com.rackspace.papi.commons.util.regex.KeyedRegexExtractor;
import com.rackspace.papi.components.clientauth.config.ClientAuthConfig;
import com.rackspace.papi.components.clientauth.openstack.config.ClientMapping;
import com.rackspace.papi.components.clientauth.openstack.v1_0.OpenStackAuthenticationHandlerFactory;
import com.rackspace.papi.components.clientauth.rackspace.config.AccountMapping;
import com.rackspace.papi.components.clientauth.rackspace.v1_1.RackspaceAuthenticationHandlerFactory;
import com.rackspace.papi.filter.logic.AbstractConfiguredFilterHandlerFactory;
import com.rackspace.papi.service.datastore.Datastore;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author jhopper
 *
 * The purpose of this class is to handle client authentication. Multiple
 * authentication schemes may be used depending on the configuration. For
 * example, a Rackspace specific or Basic Http authentication.
 *
 */
public class ClientAuthenticationHandlerFactory extends AbstractConfiguredFilterHandlerFactory<AuthModule> {

   private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ClientAuthenticationHandlerFactory.class);
   private AuthModule authenticationModule;
   private KeyedRegexExtractor<String> accountRegexExtractor = new KeyedRegexExtractor<String>();
   private final Datastore datastore;

   public ClientAuthenticationHandlerFactory(Datastore datastore) {
      this.datastore = datastore;
   }

   @Override
   protected Map<Class, UpdateListener<?>> getListeners() {
      final Map<Class, UpdateListener<?>> listenerMap = new HashMap<Class, UpdateListener<?>>();
      listenerMap.put(ClientAuthConfig.class, new ClientAuthConfigurationListener());

      return listenerMap;
   }

   private class ClientAuthConfigurationListener implements UpdateListener<ClientAuthConfig> {

      @Override
      public void configurationUpdated(ClientAuthConfig modifiedConfig) {

         if (modifiedConfig.getRackspaceAuth() != null) {
            authenticationModule = getAuth1_1Handler(modifiedConfig);
            for (AccountMapping accountMapping : modifiedConfig.getRackspaceAuth().getAccountMapping()) {
               accountRegexExtractor.addPattern(accountMapping.getIdRegex(), accountMapping.getType().value());
            }
         } else if (modifiedConfig.getOpenstackAuth() != null) {
            authenticationModule = getOpenStackAuthHandler(modifiedConfig);
            for (ClientMapping clientMapping : modifiedConfig.getOpenstackAuth().getClientMapping()) {
               accountRegexExtractor.addPattern(clientMapping.getIdRegex());
            }
         } else if (modifiedConfig.getHttpBasicAuth() != null) {
            // TODO: Create handler for HttpBasic
            authenticationModule = null;
         } else {
            LOG.error("Authentication module is not understood or supported. Please check your configuration.");
         }
      }
   }

   private AuthModule getAuth1_1Handler(ClientAuthConfig cfg) {
      return RackspaceAuthenticationHandlerFactory.newInstance(cfg, accountRegexExtractor, datastore);
   }

   private AuthModule getOpenStackAuthHandler(ClientAuthConfig config) {
      return OpenStackAuthenticationHandlerFactory.newInstance(config, accountRegexExtractor, datastore);
   }

   @Override
   protected AuthModule buildHandler() {
      return authenticationModule;
   }
}
