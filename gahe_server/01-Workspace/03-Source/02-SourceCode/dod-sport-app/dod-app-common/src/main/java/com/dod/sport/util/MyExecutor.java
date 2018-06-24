package com.dod.sport.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/9/30.
 */
public class MyExecutor {
    private static ThreadPoolExecutor borrowExecutor = null;

    /**获取标的线程池
     * @return
     */
    public static ThreadPoolExecutor getBorrowEexcutor(){
        if(borrowExecutor==null){
            borrowExecutor=new ThreadPoolExecutor(5, 10, 200,
                    TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
        }
        return borrowExecutor;
    }

}
