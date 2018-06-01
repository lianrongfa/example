package corss.proxy;

import corss.controller.Controller;
import corss.controller.FaultController;
import corss.server.netty.protocol.UART;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by lianrongfa on 2018/5/23.
 */
public class ProxyController implements InvocationHandler{

    private Controller target;

    public ProxyController(Controller target) {
        this.target = target;
    }

    public Controller getInstace() {
        Class<? extends Controller> clazz = target.getClass();
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        return (Controller)o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //预留逻辑

        Object o = method.invoke(target, args);

        //预留逻辑

        return o;
    }

}
