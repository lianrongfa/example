package corss.client;

import com.alibaba.fastjson.JSONObject;
import corss.server.socket.protocol.SimpleProduct;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ClientStart {
    public static void main(String[] args){


        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("proEquipmentNum","123");
        jsonObject.put("proType","1");
        jsonObject.put("eq26","2018-01-01 01:01:01");

        String msg = jsonObject.toJSONString();
        msg="faultJson="+msg;*/
        //socketTest("{'equipmentId':'1002'}",3);
        //System.out.println(httpRequest("http://192.168.20.140:2022/dkgl/dkgl/dkProblem.do?method=saveProblem","POST",msg));
        //nettyTest();
        socketNettyTest();
    }

    private static void nettyTest() {
        try {
            //String ip="118.26.65.50";
            String ip="127.0.0.1";
            Client client = new Client(ip, 9000);
            //client.sendMessage(new byte[]{70,0x31,0x31,0x38,0x32,0x34,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20});
            Scanner scanner = new Scanner(System.in);
            while (true){
                String s = scanner.nextLine();
                byte [] bytes={65,0x30,0x31,0x38,0x30,0x35,0x32,0x32,0x30,0x34,0x32,0x34,0x34,0x31};//消息

                /*byte[] bytes ={70,0x31,0x31,0x38,0x32,0x34,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,
                        70,0x31,0x31,0x38,0x32,0x34,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,
                        70,0x31,0x31,0x38,0x32,0x34,0x20,0x20,0x20,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
                        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30
                };*/

                //byte [] bytes={70,0x31,0x31,0x38,0x32,0x34,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20};//消息

                client.sendMessage(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *模拟下位机
     */
    private static void socketNettyTest() {
        try {
            Socket socket = new Socket("127.0.0.1", 9000);

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //String json="{'haha':'1'}";


            Scanner scanner = new Scanner(System.in);
            while (true) {

                String s = scanner.nextLine();
                //byte [] bytess1={70,0x31,0x31,0x38,0x32,0x34,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20};//手机号上传消息
                //byte [] bytess2={103,97,30};//消息
                byte [] bytess1={0x44,0x31,0x31,0x38,0x30,0x36,0x32,0x32,0x20,0x20,0x20,0x20,0x20,0x20,0x31,0x32,0x35,0x37,0x31,0x33,0x31,0x34,0x33,0x32,0x33,0x35,0x20,0x20,0x20,0x20,0x20,0x20,0x31,0x34,0x33,0x31,0x32,0x30,0x20,0x20,0x20,0x20,0x20,0x20,0x31,0x34,0x33,0x31,0x31,0x35,0x20,0x20,0x20,0x20,0x20,0x20,0x31,0x34,0x33,0x38,0x30,0x36,0x31,0x34,0x33,0x38,0x30,0x36,0x31,0x34,0x33,0x38,0x30,0x35,0x31,0x34,0x33,0x38,0x31,0x38,0x31,0x34,0x33,0x38,0x30,0x36,0x31,0x34,0x33,0x38,0x32,0x30,0x31,0x31,0x31,0x31,0x31,0x33};
                dataOutputStream.write(bytess1);//消息
                //dataOutputStream.write(bytess2);//消息

                dataOutputStream.flush();
                byte[] bytes1 = new byte[14];
                int read = dataInputStream.read(bytes1);

                for (byte b : bytes1) {
                    System.out.print(b+"\t");
                }
                System.out.println();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给中间消息服务器发送json信息
     * @param json json信息，具体格式视type类型而定
     * @param type 参照下文
     */
    private static void socketTest(String json,int type) {
        try {
            Socket socket = new Socket("127.0.0.1", 9001);

            OutputStream outputStream = socket.getOutputStream();
            //String json="{'haha':'1'}";

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            //顺序不能乱
            dataOutputStream.writeInt(0xCAFF);//消息头
            dataOutputStream.writeInt(type);//操作类型
            dataOutputStream.writeInt(json.length());//消息长度
            dataOutputStream.write(json.getBytes());//消息

            dataOutputStream.flush();

            outputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=new StringBuffer();
        try{
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写内容 也就是发起http请求需要带的参数
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);

            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();

        }
        return buffer.toString();
    }
}
