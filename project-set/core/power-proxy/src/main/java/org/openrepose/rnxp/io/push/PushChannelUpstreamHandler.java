package org.openrepose.rnxp.io.push;

import org.jboss.netty.buffer.ChannelBuffer;
import org.openrepose.rnxp.logging.ThreadStamp;
import org.openrepose.rnxp.pipe.BlockingMessagePipe;
import org.openrepose.rnxp.pipe.MessagePipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.netty.channel.*;

public class PushChannelUpstreamHandler extends SimpleChannelUpstreamHandler implements PushChannelHandler {

   private static final Logger LOG = LoggerFactory.getLogger(PushChannelUpstreamHandler.class);
   private final ChannelEventListener channelEventListener;
   private final BlockingMessagePipe<ChannelBuffer> messagePipe;
   private PushController channelPushController;

   public PushChannelUpstreamHandler(ChannelEventListener channelEventListener) {
      this.channelEventListener = channelEventListener;

      messagePipe = new BlockingMessagePipe<ChannelBuffer>();
   }

   @Override
   public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
      final ChannelBuffer input = (ChannelBuffer) e.getMessage();

      if (input.readable()) {
         channelPushController.stopMessageFlow();
         messagePipe.pushMessage(input);
      }
   }

   @Override
   public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
      final Channel channel = e.getChannel();

      channelPushController = new SimplePushController(channel);
      channelPushController.stopMessageFlow();
      
      messagePipe.setPushController(channelPushController);

      channelEventListener.channelOpen(channel, messagePipe);
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
      ThreadStamp.log(LOG, e.getCause().getMessage());
   }

   @Override
   public void channelInterestChanged(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
      ThreadStamp.log(LOG, "Channel interest changed. Interest: " + e.getChannel().getInterestOps());
   }

   @Override
   public MessagePipe<ChannelBuffer> getMessagePipe() {
      return messagePipe;
   }
}
