package top.b0x0.jmb.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import top.b0x0.jmb.service.IFileListener;

import java.io.File;

/**
 * @author wuliling Created By 2023-01-17 21:09
 **/
@Slf4j
public class FileListener extends FileAlterationListenerAdaptor {

    private final IFileListener listenerService;

    public FileListener(IFileListener listenerService) {
        this.listenerService = listenerService;
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
    }

    @Override
    public void onDirectoryCreate(File directory) {
        log.info("新建文件夹：" + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File directory) {
        log.info("修改文件夹：" + directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        log.info("删除文件夹：" + directory.getAbsolutePath());
    }

    @Override
    public void onFileCreate(File file) {
        String compressedPath = file.getAbsolutePath();
        log.info("新建文件：" + compressedPath);
        if (file.canRead()) {
            log.info("fileCanRead，进行处理");
        }
    }

    @Override
    public void onFileChange(File file) {
        String compressedPath = file.getAbsolutePath();
        log.info("文件修改：" + compressedPath);
        listenerService.updateArticleCache(compressedPath);
    }

    @Override
    public void onFileDelete(File file) {
        log.info("文件删除：" + file.getAbsolutePath());
    }


}