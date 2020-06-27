package com.simbot.modules.debugger.server;

import com.forte.qqrobot.ConfigProperties;
import com.forte.qqrobot.MsgProcessor;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.constant.PriorityConstant;
import com.simbot.modules.debugger.common.DebugController;
import com.simbot.modules.debugger.common.utils.SerializationHelper;

/**
 *
 * debugger server端配置
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@SuppressWarnings("unused")
@Beans
public class DebugServerModuleConfiguration {

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

//    /** debug server的拦截器 */
//    @Beans
//    public MsgIntercept debugServerIntercept(DebugServerConfiguration serverConf, DebugController controller){
//        boolean listen = serverConf.getListen();
//        if(listen){
//            return new DebugServerNormalIntercept(controller);
//        }else{
//            return new DebugServerDefaultMsgIntercept();
//        }
//    }

    /**
     * 初始化server，启动并构建一个 {@link DebugController}
     */
    @Beans(priority = PriorityConstant.SECOND_LAST, init = true)
    public DebugController debugServerController(DebugServer debugServer) throws Exception {
        // 启动服务
        debugServer.start();
        return new DefaultDebugServerController(debugServer);
    }

    /** 获取一个Debug server */
    @Beans(priority = PriorityConstant.SECOND_LAST)
    public DebugServer debugServer(DebugServerConfiguration serverConf){
        return serverConf.toDebugServer(msgProcessor, serializationHelper);
    }

    /** 获取一个Debug server配置类 */
    @Beans(priority = PriorityConstant.SECOND_LAST)
    public DebugServerConfiguration debugServerConfiguration(){
        DebugServerConfiguration config = new DebugServerConfiguration();
        configProperties.injectToConfig(config);
        return config;
    }



}
