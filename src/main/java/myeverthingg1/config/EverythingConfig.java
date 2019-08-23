package myeverthingg1.config;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sun.awt.image.IntegerInterleavedRaster;

/**
 * everything的配置信息
 */
@ToString
public class EverythingConfig {
    private static volatile EverythingConfig config;

    //1. 索引目录
    @Getter
    private HandlerPath handlerPath = HandlerPath.getDefaultHandlerPath();
    //2. 最大检索返回的结果数
    @Getter
    @Setter
    private Integer maxReturn = 30;

    //-------------------------------------------

    /**
     * 3.   是否开启构建索引
     *      默认：程序运行时不开启构建索引
     *      1.运行程序时，指定参数
     *      2.通过运行命令index
     */
    @Getter
    @Setter
    private Boolean enableBuildIndex = false;

    /**
     * 4.   检索时depth深度的排序规则
     *      true：表示降序
     *      false:表示升序
     *      默认是降序
     */
    @Getter
    @Setter
    private Boolean orderByDesc = false;

    /**
     * 文件监控的间隔时间10s
     */
    @Setter
    @Getter
    private Integer interval = 6000*10;
    private EverythingConfig(){
    }

    public static EverythingConfig getInstance(){
        if(config == null){
            synchronized (EverythingConfig.class){
                if(config == null){
                    config = new EverythingConfig();
                }
            }
        }
        return config;
    }


//
//    public static void main(String[] args) {
//        System.out.println( EverythingConfig.getInstance());
//    }
}
