package com.github.wulilinghan.jmb.component;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuliling Created By 2023-01-17 9:54
 **/
public class MarkDownHandler {

    /**
     * 将入参 Markdown文档转换为 html 文档
     */
    public static String mdToHtml(String md) {
        List<Extension> parserExtensions = Arrays.asList(
                // 自定义扩展，为<pre>标签添加line-numbers的class，用于prism库代码左侧行号展示
//                CodePreLineNumbersExtension.create(),
                AutolinkExtension.create(),
                EmojiExtension.create(),
                StrikethroughExtension.create(),
                TaskListExtension.create(),
                TablesExtension.create(),
                TocExtension.create()
        );
        // 按需添加扩展
        MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, parserExtensions)
                // set GitHub table parsing options
                .set(TablesExtension.WITH_CAPTION, false)
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.MIN_HEADER_ROWS, 1)
                .set(TablesExtension.MAX_HEADER_ROWS, 1)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
                // setup emoji shortcut options
                // uncomment and change to your image directory for emoji images if you have it setup
//				.set(EmojiExtension.ROOT_IMAGE_PATH, emojiInstallDirectory())
                .set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.GITHUB)
                .set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.IMAGE_ONLY);
        return mdToHtml(md, options);
    }

    private static String mdToHtml(String md, MutableDataSet options) {
        // uncomment to convert soft-breaks to hard breaks
//		options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Document document = parser.parse(md);
        return renderer.render(document);
    }

    public static String mdSimpleToHtml(String markdown) {
        if (markdown == null) {
            return "";
        }
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        Document document = parser.parse(markdown);
        return renderer.render(document);
    }
}
