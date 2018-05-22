package corss.parse;

import com.alibaba.fastjson.JSONObject;
import corss.parse.annotation.ChannelField;
import io.netty.channel.Channel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.SocketChannel;

/**
 * Created by lianrongfa on 2018/5/18.
 */
public class JsonParse<T> extends AbstractParse<T,String>{

    public JsonParse(SocketChannel channel,Class<T> clazz) {
        super(channel,clazz);
    }

    @Override
    public T parse(String m) {
        if(m!=null){
            Object o = JSONObject.parseObject(m, this.clazz);

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                ChannelField channel = field.getAnnotation(ChannelField.class);
                if(channel!=null){
                    field.setAccessible(true);
                    try {
                        field.set(o,this.channel);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            return  (T)o;
        }
        return null;
    }

    public static void main(String[] args) {
        String json="{'age':18,'name':'tom'}";
        SocketChannel socketChannel = null;
        /*try {
            socketChannel= SocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        JsonParse<Student> jsonParse = new JsonParse<Student>(socketChannel,Student.class);
        Student student = jsonParse.parse(json);
        System.out.println(student);
    }

    static class Student{
        private int age;
        private String name;

        @ChannelField
        private Channel channel;

        public Student() {
        }

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", channel=" + channel +
                    '}';
        }
    }
}
