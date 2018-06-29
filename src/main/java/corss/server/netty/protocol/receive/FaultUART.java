package corss.server.netty.protocol.receive;

import corss.server.netty.protocol.AbstractUART;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lianrongfa on 2018/5/22.
 * 故障类：设备故障上传(共9B)/作业违章上传(共24B)/启动预警上传(共9B)
 */
public class FaultUART extends AbstractUART {

    //故障时间
    private Date faultTime;

    //超时时长
    private String eq1;
    //主道口人员
    private String checkPreson;
    //副道口人员
    private String reviewPerson;

    public FaultUART() {
    }

    public FaultUART(byte[] data) {
        super(data);
    }

    public Date getFaultTime() {
        return this.faultTime;
    }

    public void setFaultTime(Date faultTime) {
        this.faultTime = faultTime;
    }

    public String getEq1() {
        return eq1;
    }

    public void setEq1(String eq1) {
        this.eq1 = eq1;
    }

    public String getCheckPreson() {
        return checkPreson;
    }

    public void setCheckPreson(String checkPreson) {
        this.checkPreson = checkPreson;
    }

    public String getReviewPerson() {
        return reviewPerson;
    }

    public void setReviewPerson(String reviewPerson) {
        this.reviewPerson = reviewPerson;
    }

    private void parseFaultTime(int from, int to) {
        byte[] bytes = Arrays.copyOfRange(getData(), from, to);

        String s = null;
        try {
            s = new String(bytes, "US-ASCII");
            if (s.length() == 12)
                s = prefix + s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Date date = buildDateTime(s);

        setFaultTime(date);
    }

    //解析超时时长
    private void parseEq1(int from, int to){
        byte[] bytes = Arrays.copyOfRange(getData(), from, to);
        if(bytes[0]==0x30){
            bytes=new byte[]{bytes[1]};
        }
        String s = asciiString(bytes);
        setEq1(s);
    }
    //解析道口主人员
    private void parseCheckPreson(int from, int to){
        byte[] bytes = Arrays.copyOfRange(getData(), from, to);
        String s = asciiString(bytes);
        setCheckPreson(s);
    }
    //解析道口副人员
    private void parseReviewPerson(int from, int to){
        byte[] bytes = Arrays.copyOfRange(getData(), from, to);
        String s = asciiString(bytes);
        setReviewPerson(s);
    }
    public void main(String[] args) {
        FaultUART uart = new FaultUART(new byte[]{65, 0x30, 0x31, 0x38, 0x30, 0x35, 0x32, 0x32, 0x30, 0x34, 0x32, 0x34, 0x34, 0x31,0x31,0x31,0x34,0x31,0x30,0x31,0x34,0x31,0x30, 0x32});
        uart.parse();
        System.out.println(uart.getFaultTime());
    }

    @Override
    public void parse() {
        parseFaultTime(2, 14);

        //新增了超时时长、道口人员编号
        if(data.length==24){
            parseEq1(14,16);
            parseCheckPreson(16,20);
            parseReviewPerson(20,24);
        }
    }
}
