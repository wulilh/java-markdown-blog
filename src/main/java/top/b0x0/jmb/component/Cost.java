package top.b0x0.jmb.component;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 耗时统计
 *
 * @author wuliling Created By 2023-01-18 15:03
 **/
@Slf4j
public class Cost implements AutoCloseable {
    private final long start;
    private final String costId;

    public Cost(String costId) {
        this.start = System.currentTimeMillis();
        this.costId = costId;
        log.info("costId: {}", costId);
    }

    public Cost() {
        this.start = System.currentTimeMillis();
        this.costId = IdUtil.simpleUUID();
        log.info("costId: {}", costId);
    }

    @Override
    public void close() {
        log.info("costId: {} , cost: {} ms", costId, (System.currentTimeMillis() - start));
    }
}