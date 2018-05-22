package corss.client;

import corss.parse.JsonParse;
import corss.server.protocol.SimpleProduct;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ClientStart {
    public static void main(String[] args){
        nettyTest();
    }

    private static void nettyTest() {
        try {
            Client client = new Client("127.0.0.1", 8080);

            Scanner scanner = new Scanner(System.in);
            while (true){
                String s = scanner.nextLine();
                client.sendMessage(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void socketTest() {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);

            OutputStream outputStream = socket.getOutputStream();


            String message = "你好  客户端";
            SimpleProduct product = new SimpleProduct(message.length(), message.getBytes());

            socket.getOutputStream().write(product.getHead_data());
            socket.getOutputStream().write(product.getContentLength());
            socket.getOutputStream().write(product.getContent());

            socket.getOutputStream().flush();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {

                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println("从服务端接收数据: " + sb);

            inputStream.close();
            outputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
