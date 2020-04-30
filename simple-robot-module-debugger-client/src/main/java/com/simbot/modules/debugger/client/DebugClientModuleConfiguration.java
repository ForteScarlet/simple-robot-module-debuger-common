package com.simbot.modules.debugger.client;

import com.forte.qqrobot.ConfigProperties;
import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.constant.PriorityConstant;
import com.simbot.modules.debugger.common.DebugController;
import com.simbot.modules.debugger.common.utils.SerializationHelper;

/**
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Beans
public class DebugClientModuleConfiguration {
    /** 消息处理器 */
    @Depend
    private MsgProcessor msgProcessor;

    /** 文件配置内容 */
    @Depend
    private ConfigProperties configProperties;

    /**
     *  格式化工具。client与server需要保持一致。
     *  默认情况下使用的为{@link com.simbot.modules.debugger.common.utils.ProtostuffSerializationHelper}
     * */
    @Depend
    private SerializationHelper serializationHelper;

    /**
     * 初始化server，启动并构建一个 {@link DebugController}
     */
    @Beans(priority = PriorityConstant.SECOND_LAST, init = true)
    public DebugController debugClientController(DebugClient debugClient) throws Exception {
        // 启动服务
        debugClient.start();
        return new DefaultDebugClientController(debugClient);
    }

    /** 获取一个Debug client */
    @Beans(priority = PriorityConstant.SECOND_LAST)
    public DebugClient debugClient(DebugClientConfiguration serverConf){
        return serverConf.toDebugClient(msgProcessor, serializationHelper);
    }

    /** 获取一个Debug server配置类 */
    @Beans(priority = PriorityConstant.SECOND_LAST)
    public DebugClientConfiguration debugClientConfiguration(){
        DebugClientConfiguration config = new DebugClientConfiguration();
        configProperties.injectToConfig(config);
        return config;
    }
}
