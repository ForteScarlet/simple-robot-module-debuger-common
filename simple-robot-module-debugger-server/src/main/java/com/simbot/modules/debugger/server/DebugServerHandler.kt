package com.simbot.modules.debugger.server

import com.forte.qqrobot.MsgProcessor
import com.forte.qqrobot.log.QQLog
import com.simbot.modules.debugger.common.DebuggerException
import com.simbot.modules.debugger.common.utils.HexUtils
import com.simbot.modules.debugger.common.utils.SerializationHelper
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.ChannelGroup

/**
 * debug server socket消息处理器
 */
class DebugServerHandler(private val group: ChannelGroup, private val msgProcessor: MsgProcessor,private val serializationHelper: SerializationHelper)
    : SimpleChannelInboundHandler<String>() {
    /**
     * 客户端与服务端创建连接时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        group.add(channel)
        QQLog.debug("debugger.server.connected", "${channel.remoteAddress()}#${channel.id()}")
}

    /**
     * 客户端与服务端断开连接时调用
     *
     * @param ctx
     * @throws Exception
     */
    override fun channelInactive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        group.remove(channel)
        QQLog.debug("debugger.server.disconnect", "${channel.remoteAddress()}#${channel.id()}")
    }

    /**
     * 服务端接收客户端发送的数据结束后调用
     *
     * @param ctx
     */
    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    /**
     * 异常调用
     * @param ctx
     * @param cause
     */
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        QQLog.warning("debugger.server.error.warning", cause.localizedMessage)
        QQLog.debug("debugger.server.error.debug", DebuggerException("error", cause, cause.localizedMessage))
        ctx.close()
    }

    /**
     * 服务端处理客户端webSocket请求的核心方法
     * @param ctx
     * @param msg
     */
    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        if (msg.startsWith("M.")) {
            val onMsg = msg.substring(2).trim { it <= ' ' }
            val data = HexUtils.toByteArray(onMsg)
            val msgGet = serializationHelper.deserialization(data)
            // 处理此debug消息
            QQLog.debug("debugger.server.onMsg", msgGet)
            msgProcessor.onMsg(msgGet)
        }else{
            QQLog.debug("debugger.server.onUnknown", msg)

        }
        // else ?
    }
}