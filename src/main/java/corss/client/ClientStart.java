package corss.client;

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

        socketTest("{'haha':'1'}",1);
        //System.out.println(httpRequest("http://127.0.0.1:8089","POST","{'hi':'1'}"));
        //nettyTest();
    }

    private static void nettyTest() {
        try {
            Client client = new Client("127.0.0.1", 8080);

            Scanner scanner = new Scanner(System.in);
            while (true){
                String s = scanner.nextLine();
                byte [] bytes={20,1,8};
                client.sendMessage(bytes);
            }
        } catch (Exception e) {
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
            Socket socket = new Socket("127.0.0.1", 8089);

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
        StringBuffer buffer=null;
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
            buffer=new StringBuffer();
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
