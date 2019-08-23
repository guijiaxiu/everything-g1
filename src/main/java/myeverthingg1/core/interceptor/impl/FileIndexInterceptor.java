package myeverthingg1.core.interceptor.impl;

import myeverthingg1.core.common.FileConvertThing;
import myeverthingg1.core.dao.FileIndexDao;
import myeverthingg1.core.interceptor.FileInterceptor;
import myeverthingg1.core.model.Thing;

import java.io.File;

/** 拦截器
 * 将File转换为Thing然后写入数据库
 */

public class FileIndexInterceptor implements FileInterceptor {
    private final FileIndexDao fileIndexDao;
    public  FileIndexInterceptor(FileIndexDao fileIndexDao){
        this.fileIndexDao = fileIndexDao;
    }
    /**
     * 打印
     * [ 转换，写入（Thing)]
     * @param file
     */
    @Override
    public void apply(File file) {
        Thing thing = FileConvertThing.convert(file); // 将file的类转换成thing 的类
        this.fileIndexDao.insert(thing);
    }
}
