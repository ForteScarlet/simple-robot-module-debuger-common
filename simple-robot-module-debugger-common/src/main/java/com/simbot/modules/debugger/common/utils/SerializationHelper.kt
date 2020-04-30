package com.simbot.modules.debugger.common.utils

import com.dyuproject.protostuff.LinkedBuffer
import com.dyuproject.protostuff.ProtostuffIOUtil
import com.dyuproject.protostuff.Schema
import com.dyuproject.protostuff.runtime.RuntimeSchema
import com.simbot.modules.debugger.common.message.DebuggerBaseMsgGet
import com.simbot.modules.debugger.common.message.debuggerMsgGetMap

/**
 * 序列化工具，工具一般可以做成一个单例对象。
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
interface SerializationHelper {
    /** 序列化，将一个[DebuggerBaseMsgGet]实例 将其序列化为一个byte数组。 */
    fun <D : DebuggerBaseMsgGet> serialization(debuggerMsgGet: D): ByteArray

    /** 反序列化，反序列化，将一串儿byte数组反序列化为一个[DebuggerBaseMsgGet]实例 */
    fun deserialization(data: ByteArray): DebuggerBaseMsgGet
}

/**
 * debugger序列化工具。单例对象，使用Protosuff进行序列化
 */
object ProtostuffSerializationHelper : SerializationHelper {
    @JvmStatic
    val instance = this

    /**
     * 获取schema实例
     */
    private fun <T> getSchema(type: Class<T>): Schema<T> = RuntimeSchema.getSchema(type)

    /** 执行序列化 */
    private fun <T : Any> doSerialization(obj: T): ByteArray {
        val schema: Schema<T> = getSchema(obj.javaClass)
        val buffer: LinkedBuffer = LinkedBuffer.allocate(DEFAULT_BUFFER_SIZE)
        return ProtostuffIOUtil.toByteArray(obj, schema, buffer)
    }

    /** 执行反序列化 */
    private fun <T> doDeserialization(data: ByteArray, type: Class<T>): T {
        val schema = getSchema(type)
        val newMessage = schema.newMessage()
        ProtostuffIOUtil.mergeFrom(data, newMessage, schema)
        return newMessage
    }

    /** 执行反序列化 */
    private fun <T : Any> doDeserialization(data: ByteArray, type: Class<T>, createFunction: () -> T) {
        val schema = getSchema(type)
        val newMessage = createFunction()
        return ProtostuffIOUtil.mergeFrom(data, newMessage, schema)
    }


    /** x序列化一个debuggerMsgGet */
    override fun <D : DebuggerBaseMsgGet> serialization(debuggerMsgGet: D): ByteArray {
        val id = debuggerMsgGet.debuggerID
        // 标记类型后返回
        val doSerialization = doSerialization(debuggerMsgGet)
        return byteArrayOf(id.toByte(), *doSerialization)
    }


    /** 反序列化一个debuggerMsgGet */
    override fun deserialization(data: ByteArray): DebuggerBaseMsgGet {
        val id = data[0]
        val type = debuggerMsgGetMap[id] ?: error("cannot found debugger bean by id: $id")
        val beanData = data.copyOfRange(1, data.size)
        return doDeserialization(beanData, type)
    }

}
