package corss.server.protocol;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public class UTRTProduct {

    private final static int MIN_LENGTH=3;

    private final Byte [] data;

    public UTRTProduct(Byte[] data) {
        this.data = data;
    }

    public Byte[] getData() {
        return data;
    }

    public Byte [] getMark(){
        if(data.length>MIN_LENGTH){
            Byte[] bytes = {data[0], data[1]};
            return bytes;
        }
        return null;
    }

    @Override
    public String toString() {
        return "UTRTProduct{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    @Test
    public void test1(){
        this.setData(new Byte []{112,115,112,114,113});;
        System.out.println(getMark());
        String m="gwwe";
        Byte[] bytes1 = {112, 115};
        Byte[] bytes2 = {112, 115};
        System.out.println(bytes1[0]==bytes2[0]);
    }
}
