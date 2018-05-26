package corss.client;

import corss.parse.JsonParse;
import corss.server.protocol.SimpleProduct;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ClientStart {
    public static void main(String[] args){

        //socketTest();
        //System.out.println(httpRequest("http://127.0.0.1:8089","POST","{'hi':'1'}"));
        nettyTest();
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

    private static void socketTest() {
        try {
            Socket socket = new Socket("127.0.0.1", 8089);

            OutputStream outputStream = socket.getOutputStream();
            String ss="{'haha':'1'}";
            SimpleProduct product = new SimpleProduct(ss.length(), ss.getBytes());

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeInt(product.getHead_data());
            dataOutputStream.writeInt(product.getContentLength());
            dataOutputStream.write(product.getContent());


            dataOutputStream.flush();

            /*InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {

                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println("从服务端接收数据: " + sb);
            inputStream.close();*/

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
