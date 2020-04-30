package com.simbot.modules.debugger.server

import com.forte.qqrobot.constant.PriorityConstant
import com.forte.qqrobot.listener.MsgGetContext
import com.forte.qqrobot.listener.MsgIntercept
import com.simbot.modules.debugger.common.DebugController
import com.simbot.modules.debugger.common.message.DebuggerBaseMsgGet

/**
 *
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
interface DebugServerMsgIntercept : MsgIntercept {
    @JvmDefault
    override fun sort() = PriorityConstant.FIRST_LAST
}


/**
 * 一切放行的拦截器
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 */
class DebugServerDefaultMsgIntercept : DebugServerMsgIntercept {

    override fun intercept(context: MsgGetContext): Boolean {
        // 永远放行
        return true
    }
}

/**
 * 拦截并推送给所有客户端
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 */
class DebugServerNormalIntercept(
        private val controller: DebugController
) : DebugServerMsgIntercept {

    /**
     * 拦截消息，如果不是debug listen，上报给客户端
     */
    override fun intercept(context: MsgGetContext): Boolean {
        val msg = context.msgGet
        if (msg !is DebuggerBaseMsgGet) {
                controller.send(msg)
        }
        // 永远放行
        return true
    }
}
