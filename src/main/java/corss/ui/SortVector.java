package corss.ui;

import java.util.Vector;

/**
 * @author lianrongfa
 * @date 2018/7/3
 */
public class SortVector<T> extends Vector<T> implements Comparable<SortVector<String>> {

    /**
     * 排序角标
     */
    public volatile static int sortIdx;

    /**
     * 排序类型 true:降序  false:升序
     */
    public volatile static boolean sortType=true;

    @Override
    public int compareTo(SortVector<String> sort) {
        String s1 = this.get(sortIdx).toString();
        String s2 = sort.get(sortIdx);
        System.out.println("s1:"+s1);
        System.out.println("s2:"+s2);
        int i = s1.compareTo(s2);
        if(sortType)
        {
            return i;
        }else{
            return -i;
        }
    }
}
