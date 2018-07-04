package corss.ui.data;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * @author lianrongfa
 * @date 2018/7/4
 */
public class ChannelTableModel extends DefaultTableModel{


    public ChannelTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    /**
     * 得到指定列的数据类型
     */
    @Override
    public Class getColumnClass(int column) {
        Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
            returnValue = getValueAt(0, column).getClass();
        } else {
            returnValue = Object.class;
        }
        //System.out.println(returnValue);
        return returnValue;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;//返回true表示能编辑，false表示不能编辑
    }
}
