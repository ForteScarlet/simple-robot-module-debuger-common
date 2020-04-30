package com.simbot.modules.debugger.common;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.simbot.modules.debugger.common.message.DebuggerBaseMsgGet;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * debug控制器，定义接口提供给server 或者 client
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public interface DebugController {

    Consumer<DebuggerBaseMsgGet> DEFAULT_DEBUG_MSG_CONSUMER = m -> {};

    /**
     * 发送一个随机类型的随机消息给远程端
     */
    default void sendRandom(){
        MsgGetTypes[] values = MsgGetTypes.values();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        send(values[random.nextInt(values.length)]);
    }
    /**
     * 发送一个随机类型的随机消息给远程端
     * @param debugMsgConsumer 对此消息做一些处理。注意数据类型的判断
     */
    default void sendRandom(Consumer<DebuggerBaseMsgGet> debugMsgConsumer){
        MsgGetTypes[] values = MsgGetTypes.values();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        send(values[random.nextInt(values.length)], debugMsgConsumer);
    }

    /**
     * 发送一个指定类型的假数据给远程端
     */
    default void send(MsgGetTypes type){
        send(type, DEFAULT_DEBUG_MSG_CONSUMER);
    };

    /**
     * 发送一个指定类型的假数据给远程端
     * @param type 发送的消息的类型
     * @param debugMsgConsumer 对此消息做一些处理
     */
    void send(MsgGetTypes type, Consumer<DebuggerBaseMsgGet> debugMsgConsumer);

    /**
     * 发送一个自己实现的MsgGet。注意，其最终还是会被转化为MsgGet实例。
     * 因此不能使用额外添加的MsgGet。其类型必须包含在{@link MsgGetTypes}中。
     * */
    void send(MsgGet msgGet);

    /**
     * 是否存活
     */
    boolean isActive();

}
