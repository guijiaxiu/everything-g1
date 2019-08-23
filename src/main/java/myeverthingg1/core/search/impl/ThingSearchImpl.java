package myeverthingg1.core.search.impl;

import myeverthingg1.core.dao.FileIndexDao;
import myeverthingg1.core.interceptor.impl.ThingClearInterceptor;
import myeverthingg1.core.model.Condition;
import myeverthingg1.core.model.Thing;
import myeverthingg1.core.search.ThingSearch;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThingSearchImpl implements ThingSearch {

    private final FileIndexDao fileIndexDao;

    private final ThingClearInterceptor interceptor;

    //Queue存放队列
    private final Queue<Thing> thingQueue = new ArrayBlockingQueue<Thing>(1024);

    public ThingSearchImpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
        this.interceptor = new ThingClearInterceptor(this.fileIndexDao,thingQueue);
        this.backgroundClearThread();
    }
    @Override
    public List<Thing> search(Condition condition) {
        //BUG:如果本地文件系统将文件删除，数据库中仍然存储到索引信息，
        // 此时如果查询结果存在已经在文件系统中删除的文件，那么需要在数据库中清除掉该文件的索引信息。
        List<Thing> things = this.fileIndexDao.query(condition);
        Iterator<Thing> iterator = things.iterator();
        while(iterator.hasNext()){
            Thing thing = iterator.next();
            File file = new File(thing.getPath());
            //如果文件不存在
            if(!file.exists()){
                //删除
                iterator.remove();//删除的操作 改成异步  后台清理
                this.thingQueue.add(thing);
            }
        }
        return things;
    }
    private void backgroundClearThread(){
        //进行一个后台清理工作(守护线程进行清理工作）
        Thread thread = new Thread(this.interceptor);
        thread.setName("Thread-Clear");
        thread.setDaemon(true);
        thread.start();
    }
}

