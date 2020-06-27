package test;

import com.forte.qqrobot.SimpleRobotApplication;
import com.forte.qqrobot.bot.BotInfo;
import com.forte.qqrobot.bot.BotManager;
import com.simbot.modules.debugger.common.DebugApplication;
import com.simbot.modules.debugger.common.DebugContext;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SimpleRobotApplication
public class DebugCommonRunApplication {
    public static void main(String[] args) throws InterruptedException {
        DebugContext run = new DebugApplication().run(DebugCommonRunApplication.class, args);

        BotManager botManager = run.getBotManager();

        for (BotInfo bot : botManager.bots()) {
            System.out.println(bot.getSender().GETTER.getLoginQQInfo());
        }

    }
}
