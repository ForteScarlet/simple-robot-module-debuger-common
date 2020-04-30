package com.simbot.modules.debugger.common;

import com.forte.qqrobot.anno.depend.Beans;
import com.simbot.modules.debugger.common.utils.ProtostuffSerializationHelper;
import com.simbot.modules.debugger.common.utils.SerializationHelper;

/**
 *
 * Debugger common 自动配置
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Beans
public class DebugCommonConfiguration {

    /** 获取序列化工具实例 */
    @Beans
    public SerializationHelper getProtostuffSerializationHelper(){
        return ProtostuffSerializationHelper.getInstance();
    }



}
