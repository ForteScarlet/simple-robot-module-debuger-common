import com.alibaba.fastjson.JSONObject
import com.forte.util.Mock
import com.simbot.modules.debugger.common.message.DebuggerGroupFileUpload

fun main() {

    Mock.scan("com.simbot.modules.debugger.common.message")
    val bean = Mock.get(DebuggerGroupFileUpload::class.java).one
    println(bean)
    val s1 = System.currentTimeMillis()
    val json = JSONObject.toJSON(bean)
    val jsonStr = json.toString()
    val s2 = System.currentTimeMillis()
    println(jsonStr)
    /*
        to json 耗时：151 ms
        to json 耗时：161 ms
        to json 耗时：140 ms
     */
    println("to json 耗时：${s2 - s1} ms")

    /* fastJson 构造报错 咳，呸 */

//    val s3 = System.currentTimeMillis()
//    val bean2 = JSONObject.parseObject(jsonStr).toJavaObject(DebuggerGroupFileUpload::class.java)
//    val s4 = System.currentTimeMillis()
//    println(bean2)
//    /*
//
//
//     */
//    println("to bean 耗时：${s4 - s3} ms")
//    println(bean == bean2)


}

