package test.listen;

import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Beans
public class DemoListener {

    /**
     * 监听私信消息，并复读
     */
    @Listen(MsgGetTypes.privateMsg)
    public void privateMsg(PrivateMsg msg, MsgSender sender){
        System.out.println("私信：" + msg.getMsg());
        sender.SENDER.sendPrivateMsg(msg, msg.getMsg());
    }

}
