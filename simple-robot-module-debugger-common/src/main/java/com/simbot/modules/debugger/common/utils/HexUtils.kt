package com.simbot.modules.debugger.common.utils

/**
 *
 * byte\[]数组转16进制工具类
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
object HexUtils {

    /**
     * byte[]转16进制字符串
     */
    fun toHex(array: ByteArray): String {
        val sb = StringBuilder(array.size * 2)
        for (b in array){
            val s = Integer.toHexString(b.toInt() and 0xff).toUpperCase()
            if(s.length == 1){
                sb.append('0').append(s)
            }else{
                sb.append(s)
            }
        }
        return sb.toString()
    }

    /**
     * hex字符串转byte数组
     */
    fun toByteArray(data: String): ByteArray {
        if(data.length % 2 != 0){
            throw IllegalArgumentException("data.length % 2 != 0")
        }
        val array = ByteArray(data.length / 2)

        val size = array.size

        var m: Int
        var n = 0

        for(i in 0 until size){
            m = i * 2
            n = m + 2
            val intVal = Integer.parseInt(data.substring(m, n), 16)
            array[i] = java.lang.Byte.valueOf(intVal.toByte())
        }

        return array
    }


}