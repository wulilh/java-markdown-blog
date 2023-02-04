package com.github.wulilinghan.jmb.component;

import com.github.wulilinghan.jmb.service.IFileListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

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

    /**
     * @param file 新建 或者 被重命名的新文件
     */
    @Override
    public void onFileCreate(File file) {
        log.info("----新建文件：" + file.getAbsolutePath());
        if (file.canRead()) {
            listenerService.addArticle(file);
        }
        log.info("----新建文件 结束");
    }

    @Override
    public void onFileChange(File file) {
        String compressedPath = file.getAbsolutePath();
        log.info("----文件修改：" + compressedPath);
        listenerService.updateArticleCache(compressedPath);
        log.info("----文件修改 结束");

    }

    /**
     * 文件重名命 也会触发
     *
     * @param file 删除的 或者 要被重名命的原文件
     */
    @Override
    public void onFileDelete(File file) {
        log.info("----文件删除：" + file.getAbsolutePath());
        listenerService.deleteArticle(file);
        log.info("----文件删除 结束");
    }


}