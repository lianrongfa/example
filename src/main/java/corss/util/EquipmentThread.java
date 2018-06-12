package corss.util;

/**
 * Created by lianrongfa on 2018/6/12.
 */
public class EquipmentThread extends Thread{

    private volatile boolean runState=true;

    @Override
    public synchronized void start() {


        super.start();
    }

    @Override
    public void run() {



    }

    public boolean isRunState() {
        return runState;
    }

    public void setRunState(boolean runState) {
        this.runState = runState;
    }
}
