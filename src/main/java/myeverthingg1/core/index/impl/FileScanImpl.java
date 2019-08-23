package myeverthingg1.core.index.impl;

import myeverthingg1.config.EverythingConfig;
import myeverthingg1.core.dao.DataSourceFactory;
import myeverthingg1.core.dao.FileIndexDao;
import myeverthingg1.core.dao.impl.FileIndexDaoImpl;
import myeverthingg1.core.index.FileScan;
import myeverthingg1.core.interceptor.FileInterceptor;
import myeverthingg1.core.interceptor.impl.FileIndexInterceptor;
import myeverthingg1.core.interceptor.impl.FilePrintInterceptor;

import javax.sql.DataSource;
import java.io.File;
import java.util.LinkedList;
import java.util.Set;

public class FileScanImpl implements FileScan {

    //存放拦截器
    private final LinkedList<FileInterceptor> interceptors = new LinkedList<>();

    private EverythingConfig config = EverythingConfig.getInstance();

    @Override
    public void index(String path) {
        //遍历目录里的子目录
        Set<String> excludePaths = config.getHandlerPath().getExcludePath();

        for(String excludePath : excludePaths){
            //如果路径在排除的路径里面任何一个里面就返回
            if(path.startsWith(excludePath)){
                // 如果再排除的目录的集合中
                return;
            }
        }

        //递归由外到内，由浅入深的去遍历目录或者文件夹
        //1.获取文件对象
        File file = new File(path);
        //2.如果文件是目录，就返回一个文件数组
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files!=null){
                for(File f : files) {
                    index(f.getAbsolutePath());
                }
            }
        }
        for(FileInterceptor interceptor:this.interceptors){
            try {
                interceptor.apply(file);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void interceptor(FileInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    /**
     * 测试代码
     * 文件递归
     */
    /*
    public static void main(String[] args) {
        FileScan scan = new FileScanImpl();
        scan.index("D:\\test");
    }
    */
}
