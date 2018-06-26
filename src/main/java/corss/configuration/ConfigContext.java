package corss.configuration;

import corss.clientn.PropertiesTest;
import corss.util.PullEquipmentId;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public class ConfigContext {

    private static Properties properties;

    private static ProtocolConfig protocolConfig=new ProtocolConfig();

    static {

        try {
            InputStream inputStream = PropertiesTest.class.getClassLoader().getResourceAsStream("configuration.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static ConfigContext configContext=new ConfigContext();

    public static ConfigContext getInstace(){
        return configContext;
    }

    /**
     * 服务端口
     */
    private int serverPort;
    private int socketPort;

    //web服务器接口地址
    private String webserverUrl;

    private ConfigContext(){
        load();
        //设备id自动维护
        new PullEquipmentId().executor();
    }

    /**
     * 读取配置文件
     */
    private void load(){
        //端口
        serverPort=Integer.valueOf(properties.getProperty("server.port","8080"));
        socketPort=Integer.valueOf(properties.getProperty("socket.port","8089"));

        //web服务器接口地址
        webserverUrl=properties.getProperty("webserver.url");

        //协议标识符
        String property = properties.getProperty("receive.container");
        for (String item :  property.split(",")) {
            protocolConfig.addReceiveType(Byte.valueOf(item));
        }

        //协议长度
        for (Byte item:protocolConfig.getReceiveContainer()) {
            String s = properties.getProperty("receive.num." + item);
            protocolConfig.getReceiveMap().put(item,Integer.valueOf(s));
        }

    }

    public String getWebserverUrl() {
        return webserverUrl;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public ProtocolConfig getProtocolConfig(){
        return protocolConfig;
    }




}
