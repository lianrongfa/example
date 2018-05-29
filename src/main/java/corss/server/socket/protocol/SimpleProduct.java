package corss.server.socket.protocol;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by lianrongfa on 2018/5/18.
 */
/**
 * <pre>
 * 自己定义的协议
 *  数据包格式
 * +——----——+——-----——+——----——+
 * |协议开始标志|  长度   |    类型     |   数据       |
 * +——----——+——-----——+——----——+
 * 1.协议开始标志head_data，为int类型的数据，16进制表示为0xCAFF
 * 2.传输数据的长度contentLength，int类型
 * 3.操作的类型@{{@link Type}}
 * 3.要传输的数据
 * </pre>
 */
public class SimpleProduct implements Serializable{
    /**
     * 消息的开头的信息标志
     */
    private int head_data = ConstantValue.HEAD_DATA;
    /**
     * 操作类型
     */
    private int type;

    /**
     * 消息的长度
     */
    private int contentLength;
    /**
     * 消息的内容
     */
    private byte[] content;


    /**
     * 用于初始化，SmartCarProtocol
     *
     * @param contentLength
     *            协议里面，消息数据的长度
     * @param content
     *            协议里面，消息的数据
     */
    public SimpleProduct(int contentLength, byte[] content,int type) {
        this.contentLength = contentLength;
        this.content = content;
        this.type=type;
    }

    public int getHead_data() {
        return head_data;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "head_data=" + head_data +
                ", type=" + type +
                ", contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
