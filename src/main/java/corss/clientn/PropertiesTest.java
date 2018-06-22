package corss.clientn;

import com.alibaba.fastjson.JSONObject;
import corss.server.netty.protocol.receive.RemoteSettingRecUART;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;



/**
 * Created by lianrongfa on 2018/5/21.
 */
public class PropertiesTest {
    public static void main(String[] args) throws Exception {

        /*String json="{\"assetId\":\"694\",\"closeRecord\":\"0\",\"dataUpload\":\"0\",\"dataUploadType\":\"0\",\"earlyWarning\":\"0\",\"fault\":\"0\",\"guardState\":\"1\",\"handrailType\":\"0\",\"marchOut\":\"0\",\"oneTwoLine\":\"0\",\"peacetimeState\":\"1\",\"reviseTime\":\"1\",\"voiceState\":\"1\"}";
        RemoteSettingSendUART remoteSettingSendUART = JSONObject.parseObject(json, RemoteSettingSendUART.class);
        System.out.println();*/
        //System.out.println(SocketClient.sendMsg("127.0.0.1",9001,"{'equipmentId':'1002'}",3));

        ThreadDemo threadDemo = new ThreadDemo();

        threadDemo.start();

        threadDemo.test();

        System.out.println(Thread.currentThread().getName());


    }
    static class ThreadDemo extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void test(){
            System.out.println(Thread.currentThread().getName());
        }
    }
    private static void fileDemo() throws IOException {
        File file = new File("C:\\Users\\lianrongfa\\Desktop\\架构篇之nginx+openresty资料\\00.readme.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"gb2312"));


        String line=null;

        while ((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }
    }

    private static void map() {
        HashMap<String, String> map = new HashMap<>();

        System.out.println(map.put("1",null));
        System.out.println(map.put("1","1"));
        System.out.println(map.put("1","2"));
        System.out.println(map.get("1"));

        /*System.out.println(map.putIfAbsent("2","3"));
        System.out.println(map.putIfAbsent("2","4"));
        System.out.println(map.putIfAbsent("2",null));*/
        System.out.println(map.get("2"));
    }

    private static void sort() {
        ArrayList<Aaa> list = new ArrayList<>();
        Aaa aaa1 = new Aaa();
        aaa1.setB(2);

        Aaa aaa2 = new Aaa();
        aaa2.setB(1);
        Aaa aaa3 = new Aaa();
        aaa3.setB(3);

        list.add(aaa1);
        list.add(aaa2);
        list.add(aaa3);
        Collections.sort(list);

        for (Aaa aaa : list) {
            System.out.println(aaa);
        }
    }

    private static void test32() {
        RemoteSettingRecUART remoteSettingRecUART = new RemoteSettingRecUART();

        byte[] bytes ={69,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                     0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30
                    ,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30
                    };
        remoteSettingRecUART.setData(bytes);
        remoteSettingRecUART.parse();

        JSONObject o = (JSONObject)JSONObject.toJSON(remoteSettingRecUART);


        System.out.println(JSONObject.toJSONString(remoteSettingRecUART));
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
        int i=1/0;
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
    static class Aaa implements Comparable<Aaa>{
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

        @Override
        public int compareTo(Aaa o) {
            if(this.b>o.getB()){
                return 1;
            }else if(b<o.getB()){
                return -1;
            }
            return 0;
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
