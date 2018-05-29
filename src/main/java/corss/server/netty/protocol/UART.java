package corss.server.netty.protocol;

/**
 * Created by lianrongfa on 2018/5/22.
 */
public interface UART {

    /**
     * 得到所有数据
     * @return byte数组
     */
    byte [] getData();

    /**
     * 设置数据
     * @param data byte数组
     */
    void setData(byte[] data);

    /**
     * 得到标识位，下标为0 1
     * @return byte.length=2 , ascii原型
     */
    byte [] getMark();

    /**
     * 得到标识位，下标为0 1
     * @return byte.length=2 ,ascii值
     */
    String getMarkString();


    /**
     * 预留接口
     * @return
     */
    byte [] getId();

    /**
     * 预留接口
     * @return
     */
    String getIdString();

    /**
     * 启动解析
     */
    void parse();
}
