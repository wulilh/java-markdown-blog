package io.github.tlh.jmb.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.EmptyFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.util.Collection;

/**
 * @author wuliling Created By 2023-01-18 11:35
 **/
public class FileUtilsTest {
    public static void main(String[] args) {
        //		过滤空文件，不读子文件夹
        Collection<File> files = FileUtils.listFiles(new File("D:/eclipse-workspace/ProgramExercise"),
                EmptyFileFilter.NOT_EMPTY, null);
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("--------------------------------------------------");

//		过滤空文件，读子文件夹
        files = FileUtils.listFiles(new File("D:/eclipse-workspace/ProgramExercise"), EmptyFileFilter.NOT_EMPTY,
                DirectoryFileFilter.INSTANCE);
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("--------------------------------------------------");

//		文件后缀为.java，读子文件夹
        files = FileUtils.listFiles(new File("D:/eclipse-workspace/ProgramExercise"), new SuffixFileFilter("java"),
                DirectoryFileFilter.INSTANCE);
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("--------------------------------------------------");

//		文件后缀为.java及.class，读子文件夹
        files = FileUtils.listFiles(new File("D:/eclipse-workspace/ProgramExercise"),
                FileFilterUtils.or(new SuffixFileFilter("java"), new SuffixFileFilter("class")),
                DirectoryFileFilter.INSTANCE);
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("--------------------------------------------------");

//		文件后缀为.java且不为空，读子文件夹
        files = FileUtils.listFiles(new File("D:/eclipse-workspace/ProgramExercise"),
                FileFilterUtils.and(new SuffixFileFilter("java"), EmptyFileFilter.NOT_EMPTY),
                DirectoryFileFilter.INSTANCE);
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
        System.out.println("--------------------------------------------------");

        Collection<File> markdownFiles = FileUtils.listFiles(new File("D:/eclipse-workspace/ProgramExercise"), null, true);

    }
}
