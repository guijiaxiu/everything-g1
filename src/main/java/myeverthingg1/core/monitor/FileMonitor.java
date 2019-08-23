package myeverthingg1.core.monitor;

import myeverthingg1.config.HandlerPath;

/**
 * 文件监控接口
 */
public interface FileMonitor {

    void start();
    /**
     * 监控
     */
    void monitor(HandlerPath handlerPath);

    void stop();
}
