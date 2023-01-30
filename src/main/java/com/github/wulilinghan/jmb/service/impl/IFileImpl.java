package com.github.wulilinghan.jmb.service.impl;

import com.github.wulilinghan.jmb.service.IFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanglinghan Created By 2023-01-30 16:38
 **/
@Service
public class IFileImpl implements IFile {

    @Override
    public Map<String, Object> uploadFile(File file, String filename) {
        Map<String, Object> map = new HashMap<>();
        //TODO
        map.put("response", "");
        map.put("imageUrl", "");
        return map;
    }
}
