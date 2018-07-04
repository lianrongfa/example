package corss.ui;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Vector;


public class Monitor extends JFrame {

    //详细数据
    private ChannelState dml = new ChannelState();
    private JTable jTable = new JTable(dml);

    //设备总数量
    private JLabel countNum;
    //已注册通道
    private JLabel registerNum;
    //未注册通道
    private JLabel uncheckedNum;
    //异常通道数量
    private JLabel warnNum;

    public Monitor() {

        init();
        windowClose();
    }

    public static void main(String args[]) {
        Monitor monitor = new Monitor();

        Random random = new Random();
        Vector<SortVector<String>> sortVectors = new Vector<>();
        for (int i = 0; i < 50; i++) {
            SortVector<String> strings = new SortVector<>();
            strings.add("10" + i);

            int i1 = random.nextInt(2);
            if (i1 == 1) {
                strings.add(ChannelState.CHANNEL_RUNNING);
            } else {
                strings.add(ChannelState.CHANNEL_CLOSED);
            }
            sortVectors.add(strings);
        }
        RowSorter sorter = new TableRowSorter(monitor.getDml());
        monitor.getjTable().setRowSorter(sorter);
        monitor.getDml().setVector(sortVectors);
        monitor.getjTable().updateUI();
    }

    /**
     * 窗口关闭
     */
    public void windowClose() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * ui初始化
     */
    public void init() {
        layoutInit(this);

        menuInit(this);

        dataUiInit(this);

        setVisible(false);
        setVisible(true);
    }

    /**
     * 数据界面初始化
     */
    private void dataUiInit(JFrame jFrame) {
        JPanel panel = new JPanel();
        jFrame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout());
        //状态总数据
        channelGather(panel);

        //详细数据
        channelDetail(panel);
    }

    private JPanel makePanel(String title, JComponent label) {
        JPanel p = new JPanel(new GridLayout(1, 1));
        p.setBorder(BorderFactory.createTitledBorder(title));
        p.add(label);
        return p;
    }

    private void channelGather(JPanel panel) {
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(1, 4));

        countNum = new JLabel("0");
        JPanel jPanel_1 = makePanel("设备总数量:", countNum);
        panel_1.add(jPanel_1);

        registerNum = new JLabel("0");
        JPanel jPanel_2 = makePanel("已注册通道数量:", registerNum);
        panel_1.add(jPanel_2);

        uncheckedNum = new JLabel("0");
        JPanel jPanel_3 = makePanel("未注册通道数量:", uncheckedNum);
        panel_1.add(jPanel_3);

        warnNum = new JLabel("0");
        JPanel jPanel_4 = makePanel("异常通道数量:", warnNum);
        panel_1.add(jPanel_4);

        panel.add(panel_1, BorderLayout.NORTH);
    }

    private void channelDetail(JPanel panel) {
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridLayout(1, 1));

        JScrollPane scrollPane = new JScrollPane(getjTable());
        panel_2.add(scrollPane);

        panel.add(panel_2, BorderLayout.CENTER);
    }

    /**
     * 布局初始化
     *
     * @param jFrame
     */
    private void layoutInit(JFrame jFrame) {
        jFrame.setTitle("道口通道监控");
        jFrame.setSize(600, 700);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    /**
     * 菜单初始化
     *
     * @param jFrame
     */
    private void menuInit(JFrame jFrame) {
        JMenuBar menuBar = new JMenuBar();
        jFrame.setJMenuBar(menuBar);

        JMenu menu = new JMenu("菜单");
        menuBar.add(menu);

        JButton button = new JButton("关于");
        menu.add(button);

    }


    public JTable getjTable() {
        return jTable;
    }

    public void setjTable(JTable jTable) {
        this.jTable = jTable;
    }

    public ChannelState getDml() {
        return dml;
    }

    public void setDml(ChannelState dml) {
        this.dml = dml;
    }

    public JLabel getCountNum() {
        return countNum;
    }

    public void setCountNum(JLabel countNum) {
        this.countNum = countNum;
    }

    public JLabel getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(JLabel registerNum) {
        this.registerNum = registerNum;
    }

    public JLabel getUncheckedNum() {
        return uncheckedNum;
    }

    public void setUncheckedNum(JLabel uncheckedNum) {
        this.uncheckedNum = uncheckedNum;
    }

    public JLabel getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(JLabel warnNum) {
        this.warnNum = warnNum;
    }
}
