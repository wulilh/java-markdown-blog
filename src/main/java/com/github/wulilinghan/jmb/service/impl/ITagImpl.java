package com.github.wulilinghan.jmb.service.impl;

import com.github.wulilinghan.jmb.common.global.GlobalData;
import com.github.wulilinghan.jmb.common.pojo.Tag;
import com.github.wulilinghan.jmb.service.ITag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuliling Created By 2023-01-30 22:08
 **/
@Service
@Slf4j
public class ITagImpl implements ITag {

    @Override
    public List<Tag> listTagTop(int i) {
        i = Math.min(i, GlobalData.tagList.size());
        return GlobalData.tagList.subList(0, i);
    }
}
