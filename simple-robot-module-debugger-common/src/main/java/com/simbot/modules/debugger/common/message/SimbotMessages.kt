package com.simbot.modules.debugger.common.message

import com.forte.qqrobot.beans.messages.msgget.*
import com.forte.qqrobot.beans.messages.types.*
import com.forte.util.mapper.MockBean
import com.forte.util.mapper.MockValue
import com.simbot.modules.debugger.common.DebuggerException
import java.io.Serializable
import java.util.*

/*
 * MsgGet 的debugger实现类
 * 序列化工具
 */

/** 对应的首位字节ID */
val debuggerMsgGetMap: Map<Byte, Class<out DebuggerBaseMsgGet>> = mapOf(
        0x01.toByte() to DebuggerPrivateMsg::class.java,
        0x02.toByte() to DebuggerGroupMsg::class.java,
        0x03.toByte() to DebuggerDiscussMsg::class.java,
        0x04.toByte() to DebuggerGroupBan::class.java,
        0x05.toByte() to DebuggerFriendAdd::class.java,
        0x06.toByte() to DebuggerGroupAdminChange::class.java,
        0x07.toByte() to DebuggerGroupMemberIncrease::class.java,
        0x08.toByte() to DebuggerGroupMemberReduce::class.java,
        0x09.toByte() to DebuggerFriendAddRequest::class.java,
        0x0A.toByte() to DebuggerGroupAddRequest::class.java,
        0x0B.toByte() to DebuggerGroupFileUpload::class.java
)


/** base msg get */
@MockBean
abstract class DebuggerBaseMsgGet(
        open val debuggerID: Int = 0,
        open var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) open var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase().toUpperCase()") open var dgId: String? = UUID.randomUUID().toString().toUpperCase().replace("-", ""),
        @MockValue("@csentence", param = "1-3") open var dgMsg: String? = null,
        open var dgTime: Long = System.currentTimeMillis(),
        @MockValue("0", param = "0-1000", valueType = Int::class) open var dgFont: String? = null
) : MsgGet, Serializable {
    companion object {
//        @JvmStatic private val serialVersionUID: Long = 0x00
    }

    override fun getOriginalData() = dgOriginalData
    override fun getThisCode() = dgThisCode
    override fun getId() = dgId
    override fun setMsg(newMsg: String?) {
        dgMsg = newMsg
    }

    override fun getTime() = dgTime
    override fun getFont() = dgFont
    override fun getMsg() = dgMsg
    override fun setThisCode(code: String?) {
        dgThisCode = code
    }
}

/** debug private msg */
@MockBean
data class DebuggerPrivateMsg(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgQQ: String? = null,
        var dgType: PrivateMsgType = PrivateMsgType.FROM_FRIEND

) : DebuggerBaseMsgGet(1, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), PrivateMsg {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x01
    }
    constructor(m: PrivateMsg): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.qq, m.type)

    override fun getQQ() = dgQQ
    override fun getType() = dgType
}

/** debugger group msg */
@MockBean
data class DebuggerGroupMsg(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null,
        var dgPowerType: PowerType = PowerType.MEMBER,
        var dgType: GroupMsgType = GroupMsgType.NORMAL_MSG
) : DebuggerBaseMsgGet(2, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), GroupMsg {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x02
    }
    constructor(m: GroupMsg): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.qq, m.group, m.powerType, m.type)


    override fun getQQ() = dgQQ
    override fun getGroup() = dgGroup
    override fun getPowerType() = dgPowerType
    override fun setPowerType(powerType: PowerType) {
        dgPowerType = powerType
    }

    override fun getType() = dgType
}

/** debugger discuss msg */
@MockBean
data class DebuggerDiscussMsg(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null
) : DebuggerBaseMsgGet(3, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), DiscussMsg {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x03
    }
    constructor(m: DiscussMsg): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.qq, m.group)


    override fun getQQ() = dgQQ
    override fun getGroup() = dgGroup
}

/** debugger group ban */
@MockBean
data class DebuggerGroupBan(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgBeOperatedQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgOperatorQQ: String? = null,
        @MockValue("0", param = "0-360", valueType = Int::class) var dgBanTime: Long = -1,
        var dgGroupBanType: GroupBanType = if (dgBanTime > 0) {
            GroupBanType.LIFT_BAN
        } else {
            GroupBanType.BAN
        }
) : DebuggerBaseMsgGet(4, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), GroupBan {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x04
    }
    constructor(m: GroupBan): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.group, m.beOperatedQQ, m.operatorQQ, m.time, m.banType)


    override fun getBeOperatedQQ() = dgBeOperatedQQ
    override fun getGroup() = dgGroup
    override fun time() = dgBanTime
    override fun getOperatorQQ() = dgOperatorQQ
    override fun getBanType() = dgGroupBanType
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
    override fun getMsg() = super<DebuggerBaseMsgGet>.getMsg()
}

/** debugger friend add */
@MockBean
data class DebuggerFriendAdd(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgQQ: String? = null
) : DebuggerBaseMsgGet(5, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), FriendAdd {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x05
    }
    constructor(m: FriendAdd): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.qq)


    override fun getQQ() = dgQQ
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
    override fun getMsg() = super<DebuggerBaseMsgGet>.getMsg()
}

/** debugger group admin change event */
@MockBean
data class DebuggerGroupAdminChange(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgBeOperatedQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgOperatorQQ: String? = null,
        var dgType: GroupAdminChangeType = GroupAdminChangeType.BECOME_ADMIN
) : DebuggerBaseMsgGet(6, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), GroupAdminChange {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x06
    }
    constructor(m: GroupAdminChange): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.group, m.beOperatedQQ, m.operatorQQ, m.type)


    override fun getBeOperatedQQ() = dgBeOperatedQQ
    override fun getGroup() = dgGroup
    override fun getOperatorQQ() = dgOperatorQQ
    override fun getType() = dgType
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
    override fun getMsg() = super<DebuggerBaseMsgGet>.getMsg()
}

/** debugger group member increase */
@MockBean
data class DebuggerGroupMemberIncrease(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgBeOperatedQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgOperatorQQ: String? = null,
        var dgType: IncreaseType = IncreaseType.AGREE
) : DebuggerBaseMsgGet(7, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), GroupMemberIncrease {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x07
    }
    constructor(m: GroupMemberIncrease): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.group, m.beOperatedQQ, m.operatorQQ, m.type)


    override fun getBeOperatedQQ() = dgBeOperatedQQ
    override fun getGroup() = dgGroup
    override fun getOperatorQQ() = dgOperatorQQ
    override fun getType() = dgType
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
    override fun getMsg() = super<DebuggerBaseMsgGet>.getMsg()
}

/** debugger group member reduce */
@MockBean
data class DebuggerGroupMemberReduce(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgBeOperatedQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgOperatorQQ: String? = null,
        var dgType: ReduceType = ReduceType.LEAVE
) : DebuggerBaseMsgGet(8, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), GroupMemberReduce {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x08
    }
    constructor(m: GroupMemberReduce): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.group, m.beOperatedQQ, m.operatorQQ, m.type)


    override fun getBeOperatedQQ() = dgBeOperatedQQ
    override fun getGroup() = dgGroup
    override fun getOperatorQQ() = dgOperatorQQ
    override fun getType() = dgType
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
    override fun getMsg() = super<DebuggerBaseMsgGet>.getMsg()
}

/** debugger friend add request */
@MockBean
data class DebuggerFriendAddRequest(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgQQ: String? = null,
        @MockValue("0", param = "1-10000", valueType = Int::class) var dgFlag: String? = null
) : DebuggerBaseMsgGet(9, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), FriendAddRequest {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x09
    }
    constructor(m: FriendAddRequest): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.qq, m.flag)


    override fun getQQ() = dgQQ
    override fun getFlag() = dgFlag
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
}

/**  debugger group add request */
@MockBean
data class DebuggerGroupAddRequest(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null,
        @MockValue("0", param = "1-10000", valueType = Int::class) var dgFlag: String? = null,
        var dgRequestType: GroupAddRequestType = GroupAddRequestType.ADD
) : DebuggerBaseMsgGet(10, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), GroupAddRequest {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x0A
    }
    constructor(m: GroupAddRequest): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.qq, m.group, m.flag, m.requestType)


    override fun getQQ() = dgQQ
    override fun getGroup() = dgGroup
    override fun getRequestType() = dgRequestType
    override fun getFlag() = dgFlag
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
}

/** debugger group file upload */
@MockBean
data class DebuggerGroupFileUpload(
        override var dgOriginalData: String? = "#DebuggerMsg#",
        @MockValue("0", param = "100000-1999999", valueType = Int::class) override var dgThisCode: String? = null,
        @MockValue("'@UUID'.replaceAll('-','').toUpperCase()") override var dgId: String? = null,
        @MockValue("@csentence", param = "1-3") override var dgMsg: String? = null,
        @MockValue("0", param = "0-1000", valueType = Int::class) override var dgFont: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgQQ: String? = null,
        @MockValue("0", param = "100000-1999999", valueType = Int::class) var dgGroup: String? = null,
        @MockValue("@word.mp4") var dgFileName: String? = null,
        @MockValue("0", param = "100-20000", valueType = Long::class) var dgFileSize: Long = -1,
        @MockValue("@word") var dgFileBusid: String? = null
) : DebuggerBaseMsgGet(11, dgOriginalData, dgThisCode, dgId, dgMsg, dgFont = dgFont), GroupFileUpload {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0x0B
    }
    constructor(m: GroupFileUpload): this(m.originalData, m.thisCode, m.id, m.msg, m.font, m.qq, m.group, m.fileName, m.fileSize, m.fileBusid)


    override fun getQQ() = dgQQ
    override fun getGroup() = dgGroup
    override fun getFileName() = dgFileName
    override fun getFileSize() = dgFileSize
    override fun getFileBusid() = dgFileBusid
    override fun setMsg(newMsg: String?) {
        super<DebuggerBaseMsgGet>.setMsg(newMsg)
    }

    override fun getFont() = super<DebuggerBaseMsgGet>.getFont()
    override fun getMsg() = super<DebuggerBaseMsgGet>.getMsg()
}



//////////// proxy debug /////////////
object ProxyDebuggerMsgGet {
    /**
     * 将一个{MsgGet}转化为{DebuggerBaseMsgGet}
     */
    infix fun toDebug(msg: MsgGet): DebuggerBaseMsgGet {
        if(msg is DebuggerBaseMsgGet){
            return msg
        }else{
            return when (msg) {
                is PrivateMsg -> DebuggerPrivateMsg(msg)
                is GroupMsg -> DebuggerGroupMsg(msg)
                is DiscussMsg -> DebuggerDiscussMsg(msg)
                is GroupBan -> DebuggerGroupBan(msg)
                is FriendAdd -> DebuggerFriendAdd(msg)
                is GroupAdminChange -> DebuggerGroupAdminChange(msg)
                is GroupMemberIncrease -> DebuggerGroupMemberIncrease(msg)
                is GroupMemberReduce -> DebuggerGroupMemberReduce(msg)
                is FriendAddRequest -> DebuggerFriendAddRequest(msg)
                is GroupAddRequest -> DebuggerGroupAddRequest(msg)
                is GroupFileUpload -> DebuggerGroupFileUpload(msg)
                else -> throw DebuggerException("noType", msg.javaClass)
            }

        }

    }

}





