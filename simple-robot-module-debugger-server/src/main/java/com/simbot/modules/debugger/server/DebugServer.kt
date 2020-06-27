package com.simbot.modules.debugger.server

import com.forte.config.Conf
import com.forte.qqrobot.MsgProcessor
import com.forte.qqrobot.beans.messages.msgget.MsgGet
import com.forte.qqrobot.beans.messages.types.MsgGetTypes
import com.forte.qqrobot.log.QQLog
import com.simbot.modules.debugger.common.DebugController
import com.simbot.modules.debugger.common.DebugMocker
import com.simbot.modules.debugger.common.Debugger
import com.simbot.modules.debugger.common.message.DebuggerBaseMsgGet
import com.simbot.modules.debugger.common.message.ProxyDebuggerMsgGet
import com.simbot.modules.debugger.common.utils.HexUtils
import com.simbot.modules.debugger.common.utils.SerializationHelper
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.io.IOException
import java.util.function.Consumer

/** DebugServer的定义 */
interface DebugServer : Debugger

/**
 * [DebugServer]的配置类
 */
@Conf("simbot.module.debugger")
data class DebugServerConfiguration(
        @Conf("port", comment = "debug server socket服务监听端口")
        var port: Int = 9998,
        @Conf("groupName", comment = "debug server socket服务记录所有客户端的group name")
        var groupName: String = "debugger_server",
        @Conf("listen", comment = "如果为true，则会注册一个消息拦截器，并上报一切非DebugMsg的监听消息")
        var listen: Boolean = false // 此属性需要核心的升级支持 - 1.12.2
) {
    /** 得到一个DebugServer实例 */
    fun toDebugServer(msgProcessor: MsgProcessor, serializationHelper: SerializationHelper): DebugServer = DebugServerImpl(port, groupName, msgProcessor, serializationHelper)
}

/**
 *
 * debug netty socket server
 *
 * @author ForteScarlet <\[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
open class DebugServerImpl(
        val port: Int,
        val groupName: String,
        val msgProcessor: MsgProcessor,
        // 序列化工具
        val serializationHelper: SerializationHelper
) : DebugServer {
    override fun closed() = close

    /** 向远程端发送一个msgGet事件 */
    override fun send(debugMsgGet: DebuggerBaseMsgGet) {
        val data = serializationHelper.serialization(debugMsgGet)
        val hex = HexUtils.toHex(data)
        synchronized(group){
            group.writeAndFlush("M.$hex.debugger")
            QQLog.debug("debugger.server.write", "$debugMsgGet")
        }
    }

    override fun started() = start

    /**
     *  关闭服务器
     */
    @Synchronized
    override fun close() {
        if (!close) {
            QQLog.debug("debugger.server.close")
            if (::channelFuture.isInitialized) {
                channelFuture.channel().close()
            }
            boosGroup.shutdownGracefully()
            workGroup.shutdownGracefully()
        }
        close = true
    }

    /** channel group */
    val group by lazy { NettyChannelGroup.getInstance(groupName) }
    /** 是否已经启动 */
    var start = false
        private set
    /** 是否已经关闭 */
    private var close = false

    lateinit var channelFuture: ChannelFuture
        private set

    val boosGroup by lazy { NioEventLoopGroup() }
    val workGroup by lazy { NioEventLoopGroup() }


    /** 启动服务 */
    @Synchronized
    override fun start() {
        if (start) {
            return
        }
        if (close) {
            throw IOException("closed")
        }
        // bootstrap
        val b = ServerBootstrap()
        b.group(boosGroup, workGroup)

        try {
            b.channel(NioServerSocketChannel::class.java)
            b.childHandler(DebugServerInitializer(group, msgProcessor, serializationHelper))// 调用自己封装的处理类
            val sync = b.bind(port).sync()
            // start
            channelFuture = sync.channel().closeFuture()
        } catch (e: Exception) {
            QQLog.error("debugger.server.start.failed", e, e.localizedMessage)
        }
        QQLog.debug("debugger.server.start.finish")
        start = true
    }
}

/**
 * [DebugServerImpl]对应的debug控制器
 */
open class DefaultDebugServerController(val server: Debugger): DebugController {
    /**
     * 发送一个类型的debug数据
     */
    override fun send(type: MsgGetTypes, debugMsgConsumer: Consumer<DebuggerBaseMsgGet>) {
        val dMsgGet = DebugMocker typeBy type
        debugMsgConsumer.accept(dMsgGet)
        send0(dMsgGet)
    }

    override fun send(msgGet: MsgGet) {
        send0(ProxyDebuggerMsgGet toDebug msgGet)
    }

    /** 是否服务存活 */
    override fun isActive() = server.started() && !server.closed()


    /** 发送一个debug msg get */
    private fun send0(debugMsgGet: DebuggerBaseMsgGet) {
        server.send(debugMsgGet)
    }



}
