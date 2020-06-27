package test;

import com.simbot.modules.debugger.common.DebugMocker;
import com.simbot.modules.debugger.common.DebugMsgProcessor;
import com.simbot.modules.debugger.common.message.DebuggerPrivateMsg;
import com.simbot.modules.debugger.common.utils.HexUtils;
import com.simbot.modules.debugger.common.utils.ProtostuffSerializationHelper;
import com.simbot.modules.debugger.common.utils.SerializationHelper;
import com.simbot.modules.debugger.server.DebugServerImpl;
import com.simbot.modules.debugger.server.NettyChannelGroup;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TestServer2 {
    public static void main(String[] args) {
        DebugMocker mocker = DebugMocker.getInstance();
        SerializationHelper serializationUtils = ProtostuffSerializationHelper.INSTANCE;

        DebugServerImpl test = new DebugServerImpl(9998, "test", new DebugMsgProcessor(), ProtostuffSerializationHelper.INSTANCE);

        test.start();

        System.out.println("run...");


        while (true) {
            NettyChannelGroup group = NettyChannelGroup.getInstance("test");
            DebuggerPrivateMsg mp = mocker.getPrivateMsg();
            byte[] serialization = serializationUtils.serialization(mp);
            String hexMsg = HexUtils.INSTANCE.toHex(serialization);
            // .debugger 切割数据
            String message = "M." + hexMsg + ".debugger";
            System.out.println(message);
            group.write(message);
            group.flush();
            System.out.println(message.length());
            try {
                 Thread.sleep(5000);
            } catch (InterruptedException ignored) { }

        }


    }
}
