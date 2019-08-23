package myeverthingg1.core.interceptor.impl;

import myeverthingg1.core.dao.FileIndexDao;
import myeverthingg1.core.interceptor.ThingInterceptor;
import myeverthingg1.core.model.Thing;

import java.util.Queue;
//检索结果thing的拦截器
public class ThingClearInterceptor implements Runnable,ThingInterceptor {
    private final FileIndexDao fileIndexDao;
    private final Queue<Thing> thingQueue;

    public ThingClearInterceptor(FileIndexDao fileIndexDao, Queue<Thing> thingQueue) {
        this.fileIndexDao = fileIndexDao;
        this.thingQueue = thingQueue;
    }

    @Override
    public void apply(Thing thing) {
        this.fileIndexDao.delete(thing);
    }

    @Override
    public void run() {
        while(true){
            try {
                //每次清理的时候sleep
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thing thing = this.thingQueue.poll();
            if(thing!=null){
                this.apply(thing);
            }
        }
    }
}
