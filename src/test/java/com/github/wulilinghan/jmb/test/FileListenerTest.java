package com.github.wulilinghan.jmb.test;

import com.github.wulilinghan.jmb.TestBase;
import com.github.wulilinghan.jmb.component.FileListenerFactory;
import jakarta.annotation.Resource;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.junit.jupiter.api.Test;

/**
 * 文件监听
 *
 * @author wuliling Created By 2023-01-17 21:11
 **/
public class FileListenerTest extends TestBase {

    @Resource
    FileListenerFactory fileListenerFactory;

    @Test
    public void test_monitor() throws Exception {
        FileAlterationMonitor monitor = fileListenerFactory.getMonitor("D:\\website\\articles");
        monitor.start();
    }
}
