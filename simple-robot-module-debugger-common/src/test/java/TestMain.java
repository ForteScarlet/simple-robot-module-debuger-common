import com.forte.lang.Language;
import com.simbot.modules.debugger.common.DebuggerException;

import java.io.IOException;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class TestMain {
    public static void main(String[] args) {
        Language.init();

        throw new DebuggerException("error", new IOException("远程主机断开连接。"));

    }
}
