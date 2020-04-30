package com.simbot.modules.debugger.common

import com.forte.qqrobot.beans.messages.types.GroupAddRequestType
import com.forte.qqrobot.sender.senderlist.RootSenderList
import com.simbot.modules.debugger.common.message.*

/**
 * debug送信器，单例唯一
 */

object DebugSenders : RootSenderList {
    override fun setGroupSign(group: String?) = true
    override fun setSign(): Boolean = true
    override fun sendDiscussMsg(group: String?, msg: String?) = "1"
    override fun sendGroupNotice(group: String?, title: String?, text: String?, top: Boolean, toNewMember: Boolean, confirm: Boolean) = true
    override fun sendLike(QQ: String?, times: Int) = true
    override fun setGroupWholeBan(group: String?, `in`: Boolean) = true
    override fun sendPrivateMsg(QQ: String?, msg: String?) = "2"
    override fun setGroupAnonymousBan(group: String?, flag: String?, time: Long) = true
    override fun setGroupMemberKick(group: String?, QQ: String?, dontBack: Boolean) = true
    override fun setDiscussLeave(group: String?) = true
    override fun setGroupAdmin(group: String?, QQ: String?, set: Boolean) = true
    override fun setGroupAnonymous(group: String?, agree: Boolean) = true
    override fun setFriendAddRequest(flag: String?, friendName: String?, agree: Boolean) = true
    override fun setGroupFileDelete(group: String?, flag: String?) = true
    override fun sendGroupMsg(group: String?, msg: String?) = "3"
    override fun setMsgRecall(flag: String?) = true
    override fun setGroupCard(group: String?, QQ: String?, card: String?) = true
    override fun setGroupAddRequest(flag: String?, requestType: GroupAddRequestType?, agree: Boolean, why: String?) = true
    override fun setGroupLeave(group: String?, dissolve: Boolean) = true
    override fun setGroupExclusiveTitle(group: String?, QQ: String?, title: String?, time: Long) = true
    override fun setGroupBan(group: String?, QQ: String?, time: Long) = true
    override fun sendFlower(group: String?, QQ: String?) = true

    override fun getGroupMemberList(group: String?) = DebugGroupMemberList()
    override fun getLoginQQInfo() = DebugLoginInfo()
    override fun getGroupTopNote(group: String?) = DebugGroupTopNote()
    override fun getGroupLinkList(group: String?, number: Int) = DebugGroupLinkList()
    override fun getBanList(group: String?) = DebugBanList()
    override fun getAuthInfo() = DebugAuthInfo()
    override fun getGroupHomeworkList(group: String?, number: Int) = DebugGroupHomeworkList()
    override fun getGroupNoteList(group: String?, number: Int) = DebugGroupNoteList()
    override fun getShareList(group: String?) = DebugShareList()
    override fun getImageInfo(flag: String?) = DebugImageInfo()
    override fun getFriendList() = DebugFriendList()
    override fun getAnonInfo(flag: String?) = DebugAnonInfo()
    override fun getGroupInfo(group: String?, cache: Boolean) = DebugGroupInfo()
    override fun getGroupList() = DebugGroupList()
    override fun getStrangerInfo(QQ: String?, cache: Boolean) = DebugStrangerInfo()
    override fun getGroupMemberInfo(group: String?, QQ: String?, cache: Boolean) = DebugGroupMemberInfo()
    override fun getFileInfo(flag: String?) = DebugFileInfo()
}


