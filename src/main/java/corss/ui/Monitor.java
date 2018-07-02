package corss.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Monitor extends JFrame {

    //详细数据
    private ChannelState dml=new ChannelState();
    private JTable jTable=new JTable(dml);

    public Monitor() {
        init();
        windowClose();
    }

    public static void main(String args[]) {
        new Monitor();
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
        panel.setLayout(new BorderLayout(5, 5));

        //状态总数据
        channelGather(panel);

        //详细数据
        channelDetail(panel);
    }

    private void channelGather(JPanel panel) {
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.ORANGE);
        panel.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    }

    private void channelDetail(JPanel panel) {
        JPanel panel_2 = new JPanel();
        //panel_2.setBackground(Color.PINK);
        panel_2.setLayout(new BorderLayout(5, 5));
        panel.add(panel_2, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(getjTable());
        panel_2.add(scrollPane, BorderLayout.CENTER);
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
}
