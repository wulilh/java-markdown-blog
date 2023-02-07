package io.github.tlh.jmb.service.impl;

import io.github.tlh.jmb.common.global.GlobalData;
import io.github.tlh.jmb.common.pojo.Tag;
import io.github.tlh.jmb.service.ITag;
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
