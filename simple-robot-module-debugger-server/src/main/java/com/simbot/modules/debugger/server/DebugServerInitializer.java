package com.simbot.modules.debugger.server;


import com.forte.qqrobot.MsgProcessor;
import com.simbot.modules.debugger.common.utils.SerializationHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 初始化连接时候的各个组件
 *
 * Created by Yuk on 2019/1/2.
 */
public class DebugServerInitializer extends ChannelInitializer<SocketChannel> {

    private ChannelGroup group;
    private MsgProcessor msgProcessor;
    private SerializationHelper serializationHelper;

    public DebugServerInitializer(ChannelGroup group, MsgProcessor msgProcessor, SerializationHelper serializationHelper){
        this.group = group;
        this.msgProcessor = msgProcessor;
        this.serializationHelper = serializationHelper;
    }

    /**
     * 定义消息接收协议
     */
    @Override
    protected void initChannel(SocketChannel e) {
        ByteBuf debuggerByteBuf = Unpooled.copiedBuffer(".debugger".getBytes());
        ByteBuf forteByteBuf = Unpooled.copiedBuffer(".forte".getBytes());
        e.pipeline().addLast("delimiterDecoder", new DelimiterBasedFrameDecoder(20480, debuggerByteBuf, forteByteBuf));
        e.pipeline().addLast("stringDecoder",new StringDecoder());
        e.pipeline().addLast("stringEncoder",new StringEncoder());
        e.pipeline().addLast("debugServerHandler",new DebugServerHandler(group, msgProcessor, serializationHelper));// 调用自己封装的处理类
    }
}