package corss.server.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class NettyContainer {

    /**
     * 存储每一个客户端接入进来时的channel对象
     */
    public final static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 通道对应设备id
     */
    public final static Map<Channel,String> sourceIds=new ConcurrentHashMap<Channel,String>();

    /**
     * 通道对应设备id
     */
    public final static Map<String,Channel> sourceChannels=new ConcurrentHashMap<String,Channel>();


    /**
     * 通道断开集合
     */
    public final static Set<String> warnSet= Collections.synchronizedSet(new HashSet<String>());


}