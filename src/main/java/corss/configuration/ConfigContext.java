package corss.configuration;

import corss.clientn.PropertiesTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by lianrongfa on 2018/5/21.
 */
public class ConfigContext {

    private static Properties properties;

    private static ProtocolConfig protocolConfig=new ProtocolConfig();

    static {
        URL url = PropertiesTest.class.getClassLoader().getResource("configuration.properties");
        try {
            FileInputStream inputStream = new FileInputStream(new File(url.getFile()));

            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private final static ConfigContext configContext=new ConfigContext();

    public static ConfigContext getInstace(){
        return configContext;
    }

    /**
     * 服务端口
     */
    private int serverPort;

    private ConfigContext(){
        load();
    }

    /**
     * 读取配置文件
     */
    private void load(){
        //端口
        serverPort=Integer.valueOf(properties.getProperty("server.port","8080"));

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

        System.out.println(protocolConfig);
    }


    public int getServerPort() {
        return this.serverPort;
    }

    public ProtocolConfig getProtocolConfig(){
        return protocolConfig;
    }




}
