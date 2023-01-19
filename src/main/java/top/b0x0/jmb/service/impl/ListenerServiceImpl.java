package top.b0x0.jmb.service.impl;

import org.springframework.stereotype.Service;
import top.b0x0.jmb.service.ListenerService;

/**
 * @author wuliling Created By 2023-01-18 20:55
 **/
@Service
public class ListenerServiceImpl implements ListenerService {


    /**
     * 文章更新时，更新文章缓存
     */
    @Override
    public void updateArticleCache(String filePath) {

    }
}
