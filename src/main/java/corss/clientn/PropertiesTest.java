package corss.clientn;

import io.netty.util.AsciiString;

import java.io.*;
import java.net.URL;
import java.util.Properties;


/**
 * Created by lianrongfa on 2018/5/21.
 */
public class PropertiesTest {
    public static void main(String[] args) {

        String s="E";

        Integer integer = Integer.valueOf(s,16);

        try {

            byte[] bytes = s.getBytes("US-ASCII");
            System.out.println();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private static void test() {
        URL url = PropertiesTest.class.getClassLoader().getResource("configuration.properties");

        File file = new File(url.getFile());

        try {
            FileInputStream inputStream = new FileInputStream(file);

            Properties properties = new Properties();
            properties.load(inputStream);

            String property = properties.getProperty("receive.container");
            String[] split = property.split(",");

            for (String item:split){

                Byte aByte = Byte.valueOf(item);
                System.out.println(aByte);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
