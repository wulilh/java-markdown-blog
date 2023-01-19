package top.b0x0.jmb.test;

import jakarta.annotation.Resource;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.junit.jupiter.api.Test;
import top.b0x0.jmb.TestBase;
import top.b0x0.jmb.component.FileListenerFactory;

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
