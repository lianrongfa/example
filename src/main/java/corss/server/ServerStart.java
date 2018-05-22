package corss.server;

import corss.clientn.PropertiesTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by lianrongfa on 2018/5/17.
 */
public class ServerStart {



    public static void main(String[] args){
        try {
            NettyServer server = new NettyServer(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
