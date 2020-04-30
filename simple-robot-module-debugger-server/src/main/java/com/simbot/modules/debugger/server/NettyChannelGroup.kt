package com.simbot.modules.debugger.server

import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor


/**
 * 记录netty服务端所连接进来的所有客户端
 */
open class NettyChannelGroup
private constructor(
        // 此ChannelGroup的ID，或者说名称，会记录在静态变量上
        val id: String,
        channelGroup: ChannelGroup
): ChannelGroup by channelGroup {
    companion object {
        /** 记录已经创建的group */
        private val channelGroupMap by lazy { mutableMapOf<String, NettyChannelGroup>() }
        /**
         * 获取或创建一个NettyChannelGroup
         */
        @JvmStatic
        @JvmOverloads
        fun getInstance(id: String, channelGroup: ChannelGroup = DefaultChannelGroup(GlobalEventExecutor.INSTANCE)) = channelGroupMap.computeIfAbsent(id) { NettyChannelGroup(id, channelGroup) }
    }

}
