package test

import com.forte.qqrobot.beans.messages.msgget.PrivateMsg
import com.forte.qqrobot.beans.messages.types.PrivateMsgType

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 */
class TestPrivateMsg : PrivateMsg {



    override fun getType(): PrivateMsgType {
        return PrivateMsgType.FROM_GROUP
    }
    override fun getQQ(): String {
        return "11010"
    }
    override fun getId(): String {
        return "id~"
    }
    override fun getMsg(): String {
        return "test hello"
    }
    override fun setMsg(newMsg: String) {}
    override fun getFont(): String {
        return "114514"
    }
    override fun getTime(): Long? {
        return System.currentTimeMillis()
    }
    override fun getOriginalData(): String {
        return "test original data"
    }
    override fun getThisCode(): String {
        return "1149159218"
    }
    override fun setThisCode(code: String) {

    }
}
