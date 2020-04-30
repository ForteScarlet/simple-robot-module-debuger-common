package com.simbot.modules.debugger.client;

import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.log.QQLog;
import com.simbot.modules.debugger.common.DebuggerException;
import com.simbot.modules.debugger.common.message.DebuggerBaseMsgGet;
import com.simbot.modules.debugger.common.utils.HexUtils;
import com.simbot.modules.debugger.common.utils.SerializationHelper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.Closeable;
import java.io.IOException;

public class DebugClientHandler extends SimpleChannelInboundHandler<String> {

    private MsgProcessor msgProcessor;

    /** 当服务器断开连接，关闭 */
    private Closeable closeable;

    private SerializationHelper serializationHelper;

    public DebugClientHandler(MsgProcessor msgProcessor, SerializationHelper serializationHelper, Closeable closeable){
        this.msgProcessor = msgProcessor;
        this.serializationHelper = serializationHelper;
        this.closeable = closeable;
    }

    /**
     * 连接成功后
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 连接成功
        Channel channel = ctx.channel();
        QQLog.debug("debugger.client.connected", channel.remoteAddress() + "#" + channel.id());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        if(msg.startsWith("M.")){
            msg = msg.substring(2).trim();
            byte[] data = HexUtils.INSTANCE.toByteArray(msg);
            DebuggerBaseMsgGet msgGet = serializationHelper.deserialization(data);
            // 处理此debug消息
            QQLog.debug("debugger.client.onMsg", msgGet);
            msgProcessor.onMsg(msgGet);
        }else{
            QQLog.debug("debugger.client.onUnknown", msg);
        }
    }

    /**
     * exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        QQLog.warning("debugger.client.error.warning", cause.getLocalizedMessage());
        QQLog.debug("debugger.client.error.debug", new DebuggerException("error", cause, cause.getLocalizedMessage()));
        ctx.close();
    }



    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws IOException {
        Channel channel = ctx.channel();
        QQLog.debug("debugger.client.disconnect", channel.remoteAddress() + "#" + channel.id());
        closeable.close();
    }

}