package cn.jtv.cross;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 * @author lianrongfa
 * @date 2018/9/3
 * 压力测试
 */
public class ClientTest implements Runnable{

    int prot;
    byte arr [];

    public ClientTest(int prot, byte[] arr) {
        this.prot = prot;
        this.arr = arr;
    }

    static Random random=new Random();

    public static void main (String args [] ){
        int size=100;

        byte arr[][]=new byte [size][];

        for (int i = 0; i < size ; i++) {
            byte[] bytes = new byte[2];
            if(i<10){
                bytes[0]=(byte)(0+48);
                bytes[1]=(byte)(i+48);
            }else{
                byte j1= (byte) ((i%10)+48);
                byte j2= (byte) (((i/10)%100)+48);
                bytes[0]=j1;
                bytes[1]=j2;
            }
            arr[i]=bytes;

        }

        for (int i = 0; i < size; i++) {
            new Thread(new ClientTest(9000,arr[i])).start();
        }



    }

    private  void socketNettyTest(int prot,byte arr []) {
        try {
            Socket socket = new Socket("127.0.0.1", prot);

            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            int i = random.nextInt(60000);

            while (true) {

                Thread.sleep(i);

                byte[] bytess2 = {70, 0x31, 0x31, 0x38, arr[0], arr[1], 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};//手机号上传消息

                byte[] bytess1 = {65, 0x30, 0x31, 0x38, 0x30, 0x35, 0x32, 0x32, 0x30, 0x34, 0x32, 0x34, 0x34, 0x31};//消息
                dataOutputStream.write(bytess2);//消息

                dataOutputStream.flush();

                dataOutputStream.write(bytess1);//消息

                dataOutputStream.flush();

                byte[] bytes1 = new byte[14];
                int read = dataInputStream.read(bytes1);

                for (byte b : bytes1) {
                    System.out.print(b + "\t");
                }
                System.out.println();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        socketNettyTest(prot,arr);
    }
}
