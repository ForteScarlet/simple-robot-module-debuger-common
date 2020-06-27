package test;

import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;
import com.simbot.modules.debugger.common.DebugApplication;
import com.simbot.modules.debugger.common.DebugContext;
import com.simbot.modules.debugger.common.DebugController;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SimpleRobotApplication
public class DebugClientRunApplication2 {
    public static void main(String[] args) throws InterruptedException {
        DebugContext run = new DebugApplication().run(DebugClientRunApplication2.class, args);

        DependCenter center = run.getDependCenter();
        BotManager botManager = run.getBotManager();

        for (BotInfo bot : botManager.bots()) {
            System.out.println(bot.getSender().GETTER.getLoginQQInfo());
        }

        System.out.println("sleep...");
        Thread.sleep(5000);

        System.out.println("get...");
        DebugController controller = center.get(DebugController.class);

        System.out.println(controller);

        while(controller.isActive()){
            controller.send(MsgGetTypes.privateMsg);
            System.out.println("send");
            Thread.sleep(1000);
        }

        System.out.println("out - not active");

    }
}
