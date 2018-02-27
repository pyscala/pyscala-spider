package com.learner.spider;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by liufangliang on 2018/1/30.
 */
public class SpiderManager {
    private  static ExecutorService threadPool;

    private SpiderManager(int threadNum){

        if(threadPool==null|| threadPool.isShutdown()){
                threadPool= Executors.newFixedThreadPool(threadNum);
        }
    }


    private static SpiderManager me;

    public static SpiderManager instance(){
        if(me==null){
            me=new SpiderManager(10);
        }
        return me;
    }

    public Spider create(){
        return new Spider();
    }

    public void start(final Spider spider){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                spider.start();
            }
        });
    }


}
