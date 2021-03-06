package org.openrepose.rnxp.http.context;

import javax.servlet.http.HttpServletResponse;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.openrepose.rnxp.http.io.control.HttpConnectionController;
import org.openrepose.rnxp.http.proxy.ExternalConnectionFuture;
import org.openrepose.rnxp.pipe.MessagePipe;

/**
 *
 * @author zinic
 */
public interface RequestContext {

    void startRequest(HttpConnectionController updateController, ExternalConnectionFuture streamController);
    
    void originConnected(Channel channel, MessagePipe<ChannelBuffer> messagePipe);

    void waitForOriginToConnect(HttpServletResponse response) throws InterruptedException;
    
    void conversationAborted();
}
