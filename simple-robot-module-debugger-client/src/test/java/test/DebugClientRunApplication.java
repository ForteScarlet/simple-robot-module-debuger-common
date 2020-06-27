package test;

import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;
import com.simbot.modules.debugger.common.DebugApplication;
import com.simbot.modules.debugger.common.DebugContext;
import com.simbot.modules.debugger.common.DebugController;

import java.util.concurrent.TimeUnit;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SimpleRobotApplication
public class DebugClientRunApplication {
    public static void main(String[] args) throws InterruptedException {
        DebugContext context = new DebugApplication().run(DebugClientRunApplication.class, args);

        // 看看 debugger 注册了什么bot info
        BotManager botManager = context.getBotManager();
        for (BotInfo bot : botManager.bots()) {
            System.out.println(bot.getSender().GETTER.getLoginQQInfo());
        }

        // 获取client中的控制器，功能与server中的控制台一致，用于向server端提交事件。
        DebugController controller = context.getController();

        // 如果连接存活，则每2秒发送一次随机的私信消息。
        while(controller.isActive()){
            controller.send(MsgGetTypes.privateMsg, msg -> {
                // 将msg中的thisCode设置为统一的账号：114514
                // 实际情况下应该设置为与你的远程端一致的账号信息
                msg.setDgThisCode("114514");
                // msg.setThisCode("114514"); // 上下两个方法一样效果

            });
            System.out.println("send private msg");
            TimeUnit.SECONDS.toMillis(2);
        }

        // 连接断开则会跳出循环
        System.out.println("out - not active");
    }
}
