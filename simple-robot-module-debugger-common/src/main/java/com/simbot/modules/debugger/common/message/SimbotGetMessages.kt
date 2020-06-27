package com.simbot.modules.debugger.common.message

import com.forte.qqrobot.beans.messages.OriginalAble
import com.forte.qqrobot.beans.messages.result.*
import com.forte.qqrobot.beans.messages.result.inner.AbstractFriend
import com.forte.qqrobot.bot.LoginInfo

/*
    定义GETTER中返回到的值。
 */

abstract class BaseDebugGetterMessages(): OriginalAble {
    override fun getOriginalData(): String = "~ debug_originalData :) ~"
}

class DebugGroupMemberList: AbstractGroupMemberList()
class DebugLoginInfo: AbstractLoginQQInfo(), LoginInfo {
    private var _name = "debugger"
    private var _qq = "1145141919"
    override fun getCode() = "1149159218"
    override fun getHeadUrl() = "http://q.qlogo.cn/headimg_dl?dst_uin=$code&spec=640"
    override fun getLevel() = 810
    override fun getName(): String = _name
    override fun getQQ(): String = _qq
    override fun setName(name: String) {
        _name = name
    }
    override fun setQq(qq: String) {
        _qq = qq
    }
}
class DebugGroupTopNote: AbstractGroupTopNote()
class DebugGroupLinkList: AbstractGroupLinkList()
class DebugBanList: AbstractBanList()
class DebugAuthInfo: AbstractAuthInfo()
class DebugGroupHomeworkList: AbstractGroupHomeworkList()
class DebugGroupNoteList: AbstractGroupNoteList()
class DebugShareList: AbstractShareList()
class DebugImageInfo: AbstractImageInfo()
class DebugFriendList: AbstractFriendList() {
    override fun getFirendList(group: String?) = arrayOf(DebugFriend(), DebugFriend())
    override fun getFriendList() = mutableMapOf("1" to arrayOf(DebugFriend(), DebugFriend()))
    class DebugFriend: AbstractFriend() {
        override fun getRemark(): String = name
        override fun getNickname(): String = name
    }
}
class DebugAnonInfo: AbstractAnonInfo()
class DebugGroupInfo: AbstractGroupInfo()
class DebugGroupList: AbstractGroupList()
class DebugStrangerInfo: AbstractStrangerInfo() {
    override fun getRemark(): String = name
    override fun getNickname(): String = name
}
class DebugGroupMemberInfo: AbstractGroupMemberInfo() {
    /** 群名片  */
    override fun getCard(): String = remark
    override fun getRemark(): String = card
}
class DebugFileInfo: AbstractFileInfo()


