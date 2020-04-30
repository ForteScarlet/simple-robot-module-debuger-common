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
    override fun getCode() = "1149159218"
    override fun getHeadUrl() = "http://q.qlogo.cn/headimg_dl?dst_uin=$code&spec=640"
    override fun getLevel() = 1145141919
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
    class DebugFriend: AbstractFriend()
}
class DebugAnonInfo: AbstractAnonInfo()
class DebugGroupInfo: AbstractGroupInfo()
class DebugGroupList: AbstractGroupList()
class DebugStrangerInfo: AbstractStrangerInfo()
class DebugGroupMemberInfo: AbstractGroupMemberInfo()
class DebugFileInfo: AbstractFileInfo()


