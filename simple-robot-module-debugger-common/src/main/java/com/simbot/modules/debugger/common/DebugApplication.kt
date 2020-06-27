package com.simbot.modules.debugger.common

import com.forte.qqrobot.*
import com.forte.qqrobot.beans.messages.msgget.MsgGet
import com.forte.qqrobot.beans.messages.types.MsgGetTypes
import com.forte.qqrobot.bot.BotInfo
import com.forte.qqrobot.bot.BotManager
import com.forte.qqrobot.bot.BotSender
import com.forte.qqrobot.bot.LoginInfo
import com.forte.qqrobot.depend.DependCenter
import com.forte.qqrobot.listener.invoker.ListenerManager
import com.forte.qqrobot.sender.senderlist.RootSenderList
import com.forte.util.utils.MockUtil
import com.simbot.modules.debugger.common.message.DebugLoginInfo
import java.util.concurrent.ThreadLocalRandom
import java.util.function.Function

/**
 * debug空配置类
 */
class DebugConfiguration : BaseConfiguration<DebugConfiguration>(){
    // 不检测bot的注册情况
    override fun getBotCheck() = false
}

/** debug的context */
class DebugContext(
        botManager  : BotManager,
        msgParser   : MsgParser,
        processor   : MsgProcessor,
        dependCenter: DependCenter,
        config: DebugConfiguration,
        application: DebugApplication
): SimpleRobotContext<DebugSenders, DebugSenders, DebugSenders, DebugConfiguration, DebugApplication>(
        DebugSenders,DebugSenders,DebugSenders,botManager,msgParser, processor, dependCenter, config, application
){
    // get controller
    val controller: DebugController by lazy { dependCenter[DebugController::class.java] }



}


/**
 * debug的一个随便的BotInfo。每次创建都会有一个随机的QQ号和一个随机的域名。如果不指定的话。
 */
data class DebugBotInfo(
        val qq: String = MockUtil.integer(8).toString(),
        val urlPath: String = MockUtil.url()
): BotInfo {
    override fun getPath() = urlPath
    override fun getInfo(): LoginInfo = DebugLoginInfo()
    override fun getBotCode() = qq
    override fun getSender() = BotSender(DebugSenders)
    override fun close() {
        // todo log?
    }
}

/**
 * 用于本地单机测试的Application启动器
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
open class DebugApplication : BaseApplication<
        DebugConfiguration,
        DebugSenders,
        DebugSenders,
        DebugSenders,
        DebugApplication,
        DebugContext
        >() {

    /** 获取debug context */
    override fun getComponentContext(defaultSenders: DefaultSenders<DebugSenders, DebugSenders, DebugSenders>, manager: BotManager, msgParser: MsgParser, processor: MsgProcessor, dependCenter: DependCenter, config: DebugConfiguration): DebugContext {
        return DebugContext(manager, msgParser, processor, dependCenter, config, this)
    }

    /** run server , 也没啥好run的*/
    override fun runServer(dependCenter: DependCenter?, manager: ListenerManager?, msgProcessor: MsgProcessor?, msgParser: MsgParser?): String {
        return "Debugger模组"
    }

    /**
     * 得到一个账号
     */
    override fun verifyBot(code: String?, info: BotInfo): BotInfo {
        return if(code != null){
            DebugBotInfo(code, info.path)
        }else{
            DebugBotInfo()
        }
    }

    /** 字符串转化，将会得到一个随机的DebuggerMsgGet */
    override fun msgParse(str: String?): MsgGet {
        val values = MsgGetTypes.values()
        val random = ThreadLocalRandom.current()
        return DebugMocker.typeBy(values[random.nextInt(values.size)])
    }

    /** 资源初始化 */
    override fun resourceInit() { }
    override fun resourceInit(config: DebugConfiguration) {
        // 随便注册一个什么
        config.registerBot("http://forte.love:1919")
    }

    override fun getDefaultSetter(botManager: BotManager?): DebugSenders? = null
    override fun getDefaultGetter(botManager: BotManager?): DebugSenders? = null
    override fun getDefaultSender(botManager: BotManager?): DebugSenders? = null

    override fun getDefaultSenders(botManager: BotManager?): DefaultSenders<DebugSenders, DebugSenders, DebugSenders> = DefaultSenders(DebugSenders, DebugSenders, DebugSenders)

    override fun getSetter(msgGet: MsgGet?, botManager: BotManager?): DebugSenders? = null
    override fun getSender(msgGet: MsgGet?, botManager: BotManager?): DebugSenders? = null
    override fun getGetter(msgGet: MsgGet?, botManager: BotManager?): DebugSenders? = null

    /** 获取送信器 */
    override fun getRootSenderFunction(botManager: BotManager?): Function<MsgGet, RootSenderList> = Function { DebugSenders }


    override fun doClose() {  }
    override fun getConfiguration(): DebugConfiguration = DebugConfiguration()
}


