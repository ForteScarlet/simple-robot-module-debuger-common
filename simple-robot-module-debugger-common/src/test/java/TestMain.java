import com.forte.lang.Language;
import com.forte.qqrobot.SimpleRobotApplication;
import com.simbot.modules.debugger.common.DebugApplication;
import com.simbot.modules.debugger.common.DebugContext;
import com.simbot.modules.debugger.common.DebuggerException;

import java.io.IOException;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SimpleRobotApplication
public class TestMain {
    public static void main(String[] args) {
        final DebugContext run = new DebugApplication().run(TestMain.class, args);



    }
}
