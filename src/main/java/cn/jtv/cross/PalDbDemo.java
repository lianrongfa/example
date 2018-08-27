package cn.jtv.cross;

import corss.configuration.ConfigContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author lianrongfa
 * @date 2018/8/22
 */
public class PalDbDemo {


    public static void main(String[] args) {
        try {
            writer();
        } catch (Exception e) {
            System.out.println(e instanceof IOException);
        }


    }

    private static void writer() throws IOException {

        throw new IOException();


    }

    private static void read(){
        ConfigContext instace = ConfigContext.getInstace();
        String server=instace.getRedisServer();
        int port=instace.getRedisPort();
        JedisPool pool=new JedisPool(server,port);

        Jedis jedis = pool.getResource();

        String hehe = jedis.get("hehe");

        System.out.println(hehe);
    }

    /**
     * 修改文件内容
     * @param fileName
     * @param oldstr
     * @param newStr
     * @return
     */
    private static boolean modifyFileContent(String fileName, String oldstr, String newStr) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileName, "rw");
            String line = null;
            long lastPoint = 0; //记住上一次的偏移量
            while ((line = raf.readLine()) != null) {
                final long ponit = raf.getFilePointer();
                if(line.contains(oldstr)){
                    String str=line.replace(oldstr, newStr);
                    raf.seek(lastPoint);
                    raf.writeBytes(str);
                }
                lastPoint = ponit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
