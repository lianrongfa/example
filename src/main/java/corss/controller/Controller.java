package corss.controller;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public interface Controller{
    /**
     * 关闭通道
     */
    void close();

    Object execute(Object ... args);
}
