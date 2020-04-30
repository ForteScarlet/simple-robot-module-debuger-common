package com.simbot.modules.debugger.client

import com.forte.config.Conf
import com.forte.qqrobot.MsgProcessor
import com.forte.qqrobot.beans.messages.msgget.MsgGet
import com.forte.qqrobot.beans.messages.types.MsgGetTypes
import com.simbot.modules.debugger.common.DebugController
import com.simbot.modules.debugger.common.DebugMocker
import com.simbot.modules.debugger.common.Debugger
import com.simbot.modules.debugger.common.message.DebuggerBaseMsgGet
import com.simbot.modules.debugger.common.message.ProxyDebuggerMsgGet
import com.simbot.modules.debugger.common.utils.HexUtils
import com.simbot.modules.debugger.common.utils.SerializationHelper
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import java.io.IOException
import java.util.function.Consumer

/**
 *
 * DebugClient，类似于DebugServer
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
interface DebugClient : Debugger


/**
 * [DebugClient]的配置类
 */
@Conf("simbot.module.debugger")
data class DebugClientConfiguration(
        @Conf("host", comment = "debug server socket连接的ip")
        var host: String = "127.0.0.1",
        @Conf("port", comment = "debug client socket连接的端口")
        var port: Int = 9998
) {
    /** 通过当前配置得到一个DebugClient */
    fun toDebugClient(msgProcessor: MsgProcessor, serializationHelper: SerializationHelper): DebugClient = DebugClientImpl(host, port, msgProcessor, serializationHelper)
}

/**
 * [DebugClient]的默认实现
 */
open class DebugClientImpl(
        val host: String,
        val port: Int,
        val msgProcessor: MsgProcessor,
        val serializationHelper: SerializationHelper
) : DebugClient {
    override fun closed() = close
    /** 是否启动 */
    var start = false
        private set
    /** 线程池 */
    val group by lazy { NioEventLoopGroup() }
    /** 是否关闭 */
    @Volatile
    private var close = false

    /** 通讯管道 */
    lateinit var channel: Channel
        private set

    override fun started() = start

    @Synchronized
    override fun start() {
        if (start) {
            return
        }
        if (close) {
            throw IOException("closed")
        }
        val b = Bootstrap()
        b.group(group) // 注册线程池
                .channel(NioSocketChannel::class.java) // 使用NioSocketChannel来作为连接用的channel类
                .remoteAddress(host, port) // 绑定连接端口和host信息
                .handler(DebugClientInitializer(msgProcessor, serializationHelper, this))
        val cf = b.connect().sync() // 异步连接服务器
        println("connected..") // 连接完成
        this.channel = cf.channel()
        start = true
    }

    /** 向服务器发送一个debug message */
    override fun send(debugMsgGet: DebuggerBaseMsgGet) {
        val data = serializationHelper.serialization(debugMsgGet)
        val hex = HexUtils.toHex(data)
        synchronized(channel) {
            channel.writeAndFlush("M.$hex.debugger")
        }
    }

    override fun close() {
        if (!close) {
            if (::channel.isInitialized) {
                channel.close()
            }
            group.shutdownGracefully()
        }
        close = true
    }

}

/**
 * debug 客户端控制器
 */
open class DefaultDebugClientController(val client: Debugger): DebugController {

    /** 对一个debug msgGet做一个处理，然后发送 */
    override fun send(type: MsgGetTypes, debugMsgConsumer: Consumer<DebuggerBaseMsgGet>) {
        val dMsgGet = DebugMocker typeBy type
        debugMsgConsumer.accept(dMsgGet)
        send0(dMsgGet)
    }

    override fun send(msgGet: MsgGet) {
        send0(ProxyDebuggerMsgGet toDebug msgGet)
    }

    override fun isActive() = client.started() && !client.closed()


    /** 发送一个debug msg get */
    private fun send0(debugMsgGet: DebuggerBaseMsgGet) {
        client.send(debugMsgGet)
    }

}
