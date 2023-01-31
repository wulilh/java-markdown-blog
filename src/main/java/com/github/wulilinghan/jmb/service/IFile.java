package com.github.wulilinghan.jmb.service;

import java.io.File;
import java.util.Map;

/**
 * @author wuliling Created By 2023-01-30 16:37
 **/
public interface IFile {

    Map<String, Object> uploadFile(File file, String filename);
}
