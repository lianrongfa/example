package corss.server.netty;

import com.alibaba.fastjson.JSONObject;
import corss.ui.data.ChannelState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
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
     * 通道断开设备id集合
     */
    public final static Set<String> warnSet= Collections.synchronizedSet(new HashSet<String>());

    /**
     * ui展示数据
     */
    public final static Map<String,ChannelState> uiData=new ConcurrentHashMap<String,ChannelState>();


    /**
     * 根据通道获取设备id
     * @param channel 通道
     * @return string 设备id
     */
    public static String getId(Channel channel){
        String id=null;
        if(channel!=null){
            id = NettyContainer.sourceIds.get(channel);
        }
        return id;
    }

    /**
     * 根据设备id获取通道
     * @param id 设备id
     * @return 通道
     */
    public static Channel getChannel(String id){
        Channel channel=null;
        if(id!=null){
            channel = NettyContainer.sourceChannels.get(id);
        }
        return channel;
    }
}