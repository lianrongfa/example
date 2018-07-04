package corss.ui;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * @author lianrongfa
 * @date 2018/7/2
 */
public class ChannelState extends AbstractTableModel {

    public static final String CHANNEL_RUNNING="已连接";
    public static final String CHANNEL_CLOSED="已断开";

    final String[] columnNames = {"设备id","通道状态"};

    private volatile Vector<SortVector> vector=new Vector<SortVector>();

    public ChannelState() {

    }

    /**
     * 得到列名
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * 重写方法，得到表格列数
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * 得到表格行数
     */
    @Override
    public int getRowCount() {
        return vector.size();
    }

    /**
     * 得到数据所对应对象
     */
    @Override
    public Object getValueAt(int row, int column) {

        if (vector!=null&&!vector.isEmpty())
            return ((SortVector<String>) vector.elementAt(row))
                    .elementAt(column);
        else
            return null;
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

    /**
     * 指定设置数据单元是否可编辑.这里设置"姓名","学号"不可编辑
     */
    /*@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex < 2)
            return false;
        else
            return true;
    }*/

    /**
     * 如果数据单元为可编辑，则将编辑后的值替换原来的值
     */
    /*@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
            *//*通知监听器数据单元数据已经改变*//*
        fireTableCellUpdated(rowIndex, columnIndex);
    }*/


    public Vector<SortVector> getVector() {
        return vector;
    }

    public void setVector(Vector<SortVector> vector) {
        this.vector = vector;
    }
}
