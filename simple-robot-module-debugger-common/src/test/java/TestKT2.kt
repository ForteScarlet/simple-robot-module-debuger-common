import com.forte.util.Mock
import com.simbot.modules.debugger.common.message.DebuggerGroupFileUpload
import com.simbot.modules.debugger.common.utils.HexUtils
import com.simbot.modules.debugger.common.utils.ProtostuffSerializationHelper

fun main() {

    Mock.scan("com.simbot.modules.debugger.common.message")
//
    val bean = Mock.get(DebuggerGroupFileUpload::class.java).one
    println(bean)
    val s1 = System.currentTimeMillis()
    val serialization = ProtostuffSerializationHelper.serialization(bean)
    val str = HexUtils.toHex(serialization)
    val s2 = System.currentTimeMillis()
    /*
        to hex 耗时：59 ms
        to hex 耗时：59 ms
        to hex 耗时：129 ms
     */
    println("to hex 耗时：${s2 - s1} ms")
    println(str)
    val s3 = System.currentTimeMillis()
    val toByteArray = HexUtils.toByteArray(str)
    val bean2 = ProtostuffSerializationHelper.deserialization(toByteArray)
    val s4 = System.currentTimeMillis()
    println(bean2)
    /*
        to bean 耗时：24 ms
        to bean 耗时：26 ms
        to bean 耗时：24 ms
     */
    println("to bean 耗时：${s4 - s3} ms")
    println(bean == bean2)


}

