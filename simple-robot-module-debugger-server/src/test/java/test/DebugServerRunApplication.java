package test;

import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.depend.DependCenter;
import com.simbot.modules.debugger.common.DebugApplication;
import com.simbot.modules.debugger.common.DebugContext;
import com.simbot.modules.debugger.common.DebugController;

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

    public static void main(String[] args) throws InterruptedException {
        DebugContext run = new DebugApplication().run(DebugServerRunApplication.class, args);

        DependCenter center = run.getDependCenter();
        BotManager botManager = run.getBotManager();

        for (BotInfo bot : botManager.bots()) {
            System.out.println(bot.getSender().GETTER.getLoginQQInfo());
        }

        System.out.println("sleep...");
        Thread.sleep(5000);

        System.out.println("get...");
        DebugController controller = center.get(DebugController.class);

        MsgProcessor msg = center.get(MsgProcessor.class);

        while(controller.isActive()){
//            msg.onMsg(new TestPrivateMsg());
//            controller.sendRandom();
//            System.out.println("send");
//            Thread.sleep(5000);
        }

        System.out.println("out - not active");

    }

}
