package com.simbot.modules.debugger.client;

import com.forte.qqrobot.MsgProcessor;
import com.simbot.modules.debugger.common.utils.SerializationHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.Closeable;

/**
 * debug 客户端连接配置
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DebugClientInitializer extends ChannelInitializer<SocketChannel> {

    private MsgProcessor msgProcessor;
    private SerializationHelper serializationHelper;
    private Closeable closeable;


    public DebugClientInitializer(MsgProcessor msgProcessor, SerializationHelper serializationHelper, Closeable closeable){
        this.msgProcessor = msgProcessor;
        this.serializationHelper = serializationHelper;
        this.closeable = closeable;
    }



    @Override
    protected void initChannel(SocketChannel ch) {
        // .debugger 切割数据
        ByteBuf debuggerByteBuf = Unpooled.copiedBuffer(".debugger".getBytes());
        ByteBuf forteByteBuf = Unpooled.copiedBuffer(".forte".getBytes());
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(20480, debuggerByteBuf, forteByteBuf));
        ch.pipeline().addLast(new StringDecoder());
        ch.pipeline().addLast(new StringEncoder());
        ch.pipeline().addLast(new DebugClientHandler(msgProcessor, serializationHelper, closeable));
    }

}
