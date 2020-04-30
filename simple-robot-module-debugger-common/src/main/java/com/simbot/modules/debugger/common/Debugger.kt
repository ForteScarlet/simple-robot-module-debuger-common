package com.simbot.modules.debugger.common

import com.simbot.modules.debugger.common.message.DebuggerBaseMsgGet
import java.io.Closeable

/**
 *
 * debugger 接口，定义一个远程端的debugger
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
interface Debugger: Closeable {
        /** 判断是否已经启动 */
        fun started(): Boolean
        /** 启动服务 */
        @Throws(Exception::class)
        fun start()
        // 向远程端发送一个msg get
        fun send(debugMsgGet: DebuggerBaseMsgGet)
        fun closed(): Boolean
}