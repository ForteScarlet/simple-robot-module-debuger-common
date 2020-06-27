package test;

import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;
import com.simbot.modules.debugger.common.DebugApplication;
import com.simbot.modules.debugger.common.DebugContext;
import com.simbot.modules.debugger.common.DebugController;
import com.simbot.modules.debugger.common.message.DebuggerPrivateMsg;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 启动器，启动器所在的位置至少需要一层包路径。
 *
 * 此Demo使用的是cqhttp组件。
 * 记得配置好酷Q那边。
 * 文档：http://simple-robot-doc.forte.love/1519393
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
// 如果不用接口启动，则记得标注注解。默认的配置文件的文件名为：simple-robot-conf.properties
@SimpleRobotApplication
public class DebugServerRunApplication {

    public static void main(String[] args) {
        DebugContext context = new DebugApplication().run(DebugServerRunApplication.class, args);
        // debug控制器, 此处是server controller
        DebugController controller = context.getController();
        // 如果要主动通过debug server向client提交事件：
        // send(...), 这个是发送指定类型的消息
//        controller.send(MsgGetTypes.privateMsg, msg -> {
//            System.out.println("随机的msg为：" + msg);
//            // 手动设置bot的账号为一个固定值
//            msg.setThisCode("1149159218");
//        });
        // 随机消息
//        controller.sendRandom();
        // 随机消息
//        controller.sendRandom(msg -> {
//            System.out.println("随机的msg为：" + msg);
//            // 手动设置bot的账号为一个固定值
//            msg.setThisCode("1149159218");
//        });

        // 如果 server被关闭了，则会跳出循环，执行下一句
        while(controller.isActive()){  }
        System.out.println("out - not active");
    }
}
