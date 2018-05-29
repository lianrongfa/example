package corss.clientn;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Pattern;



/**
 * Created by lianrongfa on 2018/5/21.
 */
public class PropertiesTest {
    public static void main(String[] args) throws UnsupportedEncodingException {

        String json="{'b':31}";
        Aaa aaa = JSONObject.parseObject(json, Aaa.class);
        System.out.println();
    }

    private static void refex() {
        try {
            Field a = Aa.class.getDeclaredField("a");
            Aa aa = new Aa();
            aa.setA((char)48);

            a.setAccessible(true);
            Object o = a.get(aa);
            String s = o.toString();
            System.out.println(s);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void jjj(String s){

    }

    private static void test3() {
        String s1="      ";
        String regex="\\s+";
        boolean matches = Pattern.matches(regex, s1);
        System.out.println(matches);
        byte[] bytes = s1.getBytes();
        System.out.println();
    }

    static class Aa extends Aaa{
        private char a;

        public char getA() {
            return a;
        }

        public void setA(char a) {
            this.a = a;
        }
    }
    static class Aaa{
        private int b;
        private char c;

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public char getC() {
            return c;
        }

        public void setC(char c) {
            this.c = c;
        }
    }
    private static void test2() {
        char a=' ';

        try {
            System.out.println(new String(new byte[]{0x31,0x38,0x30,0x35,0x32,0x32},"US-ASCII"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void tset2() {
        String s="180522052441";

        //Integer integer = Integer.valueOf(s,16);

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
