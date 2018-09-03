package cn.jtv.cross;

import java.util.concurrent.*;

/**
 * @author lianrongfa
 * @date 2018/8/30
 */
public class Test implements Runnable{
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {

            Xixi xixi = new Xixi(r);


            return xixi;
        }
    });

    static class Xixi extends Thread{

        public Xixi(Runnable target) {
            super(target);
        }

        @Override
        public void run() throws RuntimeException{
                super.run();
        }
    }

    int i=0;

    public static void main(String[] args) {
        Test test = new Test();
        executorService.scheduleWithFixedDelay(test,0,5, TimeUnit.SECONDS);

        /*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.NANOSECONDS,
                new ArrayBlockingQueue<Runnable>(16), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("123123");
            }
        });

        threadPoolExecutor.execute(test);*/
    }

    @Override
    public void run() {
        while (true){
            try {
                if(i<10){
                    i++;

                    System.out.println(this.i);
                    if(i>5){
                        int i=1/0;

                    }
                }else if(i==10){
                    i=0;
                    System.out.println("退出");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ThreadPoolExecutor pools=new ThreadPoolExecutor(0, 10, 0,TimeUnit.SECONDS,new SynchronousQueue<Runnable>()){
        private Runnable warp(final Runnable task,final Exception clientStack, String clientThreadName){
            return new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } catch (Exception e) {
                        clientStack.printStackTrace();
                        throw e;
                    }
                }
            };
        }
        private Exception clientTrace(){
            return new Exception("client stack trace");
        }
        @Override
        public void execute(Runnable command) {
            // TODO Auto-generated method stub
            super.execute(warp(command,clientTrace(), Thread.currentThread().getName()));
        }
        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(warp(task,clientTrace(),Thread.currentThread().getName()));
        }
    };
}
