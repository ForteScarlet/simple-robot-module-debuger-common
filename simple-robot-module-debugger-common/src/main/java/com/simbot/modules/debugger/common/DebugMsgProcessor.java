package com.simbot.modules.debugger.common;

import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.listener.result.ListenResultImpl;
import com.forte.qqrobot.log.QQLog;

import java.util.function.Consumer;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class DebugMsgProcessor extends MsgProcessor {

    private Consumer<MsgGet> onMsgConsumer;

    public DebugMsgProcessor() {
        super(null, null, null);
        this.onMsgConsumer = m -> QQLog.debug("debugger.processor.on", m);
    }

    public DebugMsgProcessor(Consumer<MsgGet> onMsgConsumer) {
        super(null, null, null);
        this.onMsgConsumer = onMsgConsumer;
    }
    private ListenResult[] results = new ListenResult[0];
    private ListenResult defaultResult = new ListenResultImpl();

    @Override
    public ListenResult[] onMsg(MsgGet msgGet) {
        onMsgConsumer.accept(msgGet);
        return results;
    }
    @Override
    public ListenResult onMsgSelected(MsgGet msgGet) {
        onMsgConsumer.accept(msgGet);
        return defaultResult;
    }
}
