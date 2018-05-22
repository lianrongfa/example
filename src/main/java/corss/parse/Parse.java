package corss.parse;

/**
 * Created by lianrongfa on 2018/5/18.
 */
public interface Parse<T,M> {
    /**
     * 对客户端数据进行解析
     * @param m 客户端传输的真实数据
     * @return 转换的对象
     */
    T parse(M m);
}
