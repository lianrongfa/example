package corss.proxy;

import corss.controller.*;
import corss.server.netty.protocol.UART;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lianrongfa on 2018/5/31.
 */
public class SimpleFactory {

    private static Map<Byte,Class> controllerMap =new HashMap<Byte,Class>();

    static {
        controllerMap.put((byte) 65,FaultController.class);
        controllerMap.put((byte) 66,FaultController.class);
        controllerMap.put((byte) 67,FaultController.class);
        controllerMap.put((byte) 70,ChannelController.class);
        controllerMap.put((byte) 71,ForwardController.class);
        controllerMap.put((byte) 69,SettingController.class);
    }

    public static Controller createController(ChannelHandlerContext ctx, UART uart){

        byte mark = uart.getMark()[0];
        Class clazz = controllerMap.get(mark);
        Controller controller = null;
        if (clazz!=null){
            try {
                Constructor constructor = clazz.getConstructor(ChannelHandlerContext.class, UART.class);

                controller = (Controller) constructor.newInstance(ctx, uart);

                ProxyController proxy = new ProxyController(controller);

                Controller controllerProxy = proxy.getInstace();
                return controllerProxy;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
