package org.openrepose.rnxp.http.proxy;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 *
 * @author zinic
 */
public class OriginChannelFactory {

   private final ClientSocketChannelFactory cf;

   public OriginChannelFactory() {
      cf = new NioClientSocketChannelFactory(
              Executors.newCachedThreadPool(),
              Executors.newCachedThreadPool());
   }

   public ChannelFuture connect(InetSocketAddress addr, ChannelPipelineFactory pipelineFactory) {
      final ClientBootstrap clientBootstrap = new ClientBootstrap(cf);
      clientBootstrap.setPipelineFactory(pipelineFactory);
      
      return clientBootstrap.connect(addr);
   }
}
