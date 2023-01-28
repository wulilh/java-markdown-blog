package top.b0x0.jmb.component;

import jakarta.annotation.Resource;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.stereotype.Component;
import top.b0x0.jmb.service.IFileListener;

import java.io.File;

/**
 * @author wuliling Created By 2023-01-17 21:11
 **/
@Component
public class FileListenerFactory {

    @Resource
    private IFileListener listenerService;

    /**
     * @param monitorDir 监听路径
     * @param interval   轮询间隔，单位毫秒
     * @return /
     */
    public FileAlterationMonitor getMonitor(String monitorDir, long interval) {
        // 创建过滤器
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(),
                // 只会监控Markdown文件的变动
                FileFilterUtils.suffixFileFilter(".md"));
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        // 装配过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir), filter);

        // 向监听者添加监听器，并注入业务服务
        observer.addListener(new FileListener(listenerService));

        // 返回监听者
        return new FileAlterationMonitor(interval, observer);
    }

    /**
     * @param monitorDir 监听路径
     * @return /
     */
    public FileAlterationMonitor getMonitor(String monitorDir) {
        return getMonitor(monitorDir, 5000);
    }
}