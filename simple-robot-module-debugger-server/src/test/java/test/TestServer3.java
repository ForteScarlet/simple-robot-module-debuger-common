package test;

import com.forte.lang.Language;
import com.forte.qqrobot.log.LogLevel;
import com.forte.qqrobot.log.QQLog;
import com.simbot.modules.debugger.common.DebugMsgProcessor;
import com.simbot.modules.debugger.common.utils.ProtostuffSerializationHelper;
import com.simbot.modules.debugger.server.DebugServer;
import com.simbot.modules.debugger.server.DebugServerConfiguration;
import com.simbot.modules.debugger.server.DefaultDebugServerController;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TestServer3 {
    public static void main(String[] args) throws Exception {
        Language.init();
        QQLog.setGlobalLevel(LogLevel.DEBUG);

        DebugServerConfiguration conf = new DebugServerConfiguration();
        DebugServer server = conf.toDebugServer(new DebugMsgProcessor(), ProtostuffSerializationHelper.INSTANCE);
        server.start();
        DefaultDebugServerController controller = new DefaultDebugServerController(server);

        for (;;) {
            controller.sendRandom();
            System.out.println("send...");
            Thread.sleep(3000);
        }

//        System.out.println("close");
//        server.close();
//        System.out.println("closed");
    }
}
