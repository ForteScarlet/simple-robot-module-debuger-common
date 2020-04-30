import com.alibaba.fastjson.JSONObject
import com.forte.qqrobot.utils.DefaultHttpClientTemplate
import com.forte.util.Mock
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 *
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
fun main() {
    val http = DefaultHttpClientTemplate()
    val url = "http://localhost:15514/coolq/listen"

    val json = """
        {
            "post_type": "message",
            "message_type": "private",
            "sub_type": "friend",
            "message_id|1-100": 0,
            "user_id|10000000-19999999": 0,
            "message": "@csentence",
            "raw_message": "@csentence",
            "font|400-500": 0,
            "sender": {
                "nickname": "@cname",
                "sex": ["male", "female", "unknown"],
                "age|10-40": 0
            }
        }
    """.trimIndent()

    val mockMap = JSONObject.parseObject(json)

    val setSt = System.currentTimeMillis()

    Mock.set(CQHttpPrivateMsg::class.java, mockMap)

    println("解析耗时：${System.currentTimeMillis() - setSt} ms")

    val msgMock = Mock.get(CQHttpPrivateMsg::class.java)


    println(msgMock.one)

    var num = AtomicLong(0)
    var times = AtomicInteger(0)

    var r = ThreadLocalRandom.current()

    val launch = GlobalScope.launch {
        while (isActive) {
            GlobalScope.launch {
                val s = System.currentTimeMillis()
                http.postJson(url, JSONObject.toJSONString(msgMock.one))
                val e = System.currentTimeMillis()
                num.addAndGet(e - s)
                times.addAndGet(1)
                delay(r.nextLong(300) + 200)
            }
        }
    }

    Scanner(System.`in`).nextLine()
    runBlocking {
        println("cancel and join")
        launch.cancelAndJoin()
    }


    println("num:   $num ms")
    println("times: $times times")
    println("avg  : ${num.get() / times.get()} ms/times")





}

/*

 */

data class CQHttpPrivateMsg(
        var time: Long = System.currentTimeMillis() - 2,
        var post_type: String? = null,
        var message_type: String? = null,
        var sub_type: String = "friend",
        var message_id: Int = 12,
        var user_id: Int = 1149159218,
        var message: String = "hi",
        var raw_message: String = "hi",
        var font: Int = 456,
        var sender: CQHttpPrivateMsgSender = CQHttpPrivateMsgSender()
)

/**
 * sender
 *  "nickname": "小不点",
    "sex": "male",
    "age": 18
 */
data class CQHttpPrivateMsgSender(
    var nickname: String = "nickname",
    var sex: String = "male",
    var age: Int = 18
)