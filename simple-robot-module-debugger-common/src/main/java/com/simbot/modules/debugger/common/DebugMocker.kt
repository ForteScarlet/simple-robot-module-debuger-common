package com.simbot.modules.debugger.common

import com.forte.qqrobot.beans.messages.types.MsgGetTypes
import com.forte.util.Mock
import com.simbot.modules.debugger.common.message.*

/**
 * debug mocker
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
object DebugMocker {
    init {
        Mock.scan("com.simbot.modules.debugger.common.message")
    }

    private val mockPrivateMsg by lazy { Mock.get(DebuggerPrivateMsg::class.java) }
    private val mockGroupMsg by lazy { Mock.get(DebuggerGroupMsg::class.java) }
    private val mockDiscussMsg by lazy { Mock.get(DebuggerDiscussMsg::class.java) }
    private val mockGroupBan by lazy { Mock.get(DebuggerGroupBan::class.java) }
    private val mockFriendAdd by lazy { Mock.get(DebuggerFriendAdd::class.java) }
    private val mockGroupAdminChange by lazy { Mock.get(DebuggerGroupAdminChange::class.java) }
    private val mockGroupMemberIncrease by lazy { Mock.get(DebuggerGroupMemberIncrease::class.java) }
    private val mockGroupMemberReduce by lazy { Mock.get(DebuggerGroupMemberReduce::class.java) }
    private val mockFriendAddRequest by lazy { Mock.get(DebuggerFriendAddRequest::class.java) }
    private val mockGroupAddRequest by lazy { Mock.get(DebuggerGroupAddRequest::class.java) }
    private val mockGroupFileUpload by lazy { Mock.get(DebuggerGroupFileUpload::class.java) }

    @JvmStatic
    val instance = this

    val privateMsg: DebuggerPrivateMsg get() = mockPrivateMsg.one
    val groupMsg: DebuggerGroupMsg get() = mockGroupMsg.one
    val discussMsg: DebuggerDiscussMsg get() = mockDiscussMsg.one
    val groupBan: DebuggerGroupBan get() = mockGroupBan.one
    val friendAdd: DebuggerFriendAdd get() = mockFriendAdd.one
    val groupAdminChange: DebuggerGroupAdminChange get() = mockGroupAdminChange.one
    val groupMemberIncrease: DebuggerGroupMemberIncrease get() = mockGroupMemberIncrease.one
    val groupMemberReduce: DebuggerGroupMemberReduce get() = mockGroupMemberReduce.one
    val friendAddRequest: DebuggerFriendAddRequest get() = mockFriendAddRequest.one
    val groupAddRequest: DebuggerGroupAddRequest get() = mockGroupAddRequest.one
    val groupFileUpload: DebuggerGroupFileUpload get() = mockGroupFileUpload.one

    infix fun typeBy(type: MsgGetTypes): DebuggerBaseMsgGet {
        return when(type) {
            MsgGetTypes.privateMsg -> privateMsg
            MsgGetTypes.discussMsg -> discussMsg
            MsgGetTypes.groupBan -> groupBan
            MsgGetTypes.groupMsg -> groupMsg
            MsgGetTypes.friendAdd -> friendAdd
            MsgGetTypes.groupAdminChange -> groupAdminChange
            MsgGetTypes.groupMemberIncrease -> groupMemberIncrease
            MsgGetTypes.groupMemberReduce -> groupMemberReduce
            MsgGetTypes.friendAddRequest -> friendAddRequest
            MsgGetTypes.groupAddRequest -> groupAddRequest
            MsgGetTypes.groupFileUpload -> groupFileUpload
            else -> privateMsg
        }
    }

    fun getPrivateMsg(num: Int) = if(num <= 1) { mutableListOf(privateMsg) } else { mockPrivateMsg.getList(num) }
    fun getGroupMsg (num: Int) = if(num <= 1) { mutableListOf(groupMsg) } else { mockGroupMsg.getList(num) }
    fun getDiscussMsg (num: Int) = if(num <= 1) { mutableListOf(discussMsg) } else { mockDiscussMsg.getList(num) }
    fun getGroupBan (num: Int) = if(num <= 1) { mutableListOf(groupBan) } else { mockGroupBan.getList(num) }
    fun getFriendAdd (num: Int) = if(num <= 1) { mutableListOf(friendAdd) } else { mockFriendAdd.getList(num) }
    fun getGroupAdminChange (num: Int) = if(num <= 1) { mutableListOf(groupAdminChange) } else { mockGroupAdminChange.getList(num) }
    fun getGroupMemberIncrease (num: Int) = if(num <= 1) { mutableListOf(groupMemberIncrease) } else { mockGroupMemberIncrease.getList(num) }
    fun getGroupMemberReduce (num: Int) = if(num <= 1) { mutableListOf(groupMemberReduce) } else { mockGroupMemberReduce.getList(num) }
    fun getFriendAddRequest (num: Int) = if(num <= 1) { mutableListOf(friendAddRequest) } else { mockFriendAddRequest.getList(num) }
    fun getGroupAddRequest (num: Int) = if(num <= 1) { mutableListOf(groupAddRequest) } else { mockGroupAddRequest.getList(num) }
    fun getGroupFileUpload (num: Int) = if(num <= 1) { mutableListOf(groupFileUpload) } else { mockGroupFileUpload.getList(num) }

}