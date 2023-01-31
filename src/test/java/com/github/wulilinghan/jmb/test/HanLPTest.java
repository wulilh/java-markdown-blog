package com.github.wulilinghan.jmb.test;

import com.hankcs.hanlp.HanLP;
import org.junit.jupiter.api.Test;
import com.github.wulilinghan.jmb.TestBase;

public class HanLPTest extends TestBase {

    @Test
    public void test() {
        String text = "我国古代历史演义小说的代表作。明代小说家罗贯中依据有关三国的历史、杂记，在广泛吸取民间传说和民间艺人创作成果的基础上，加工、再创作了这部长篇章回小说。" +
                "作品写的是汉末到晋初这一历史时期魏、蜀、吴三个封建统治集团间政治、军事、外交等各方面的复杂斗争。通过这些描写，揭露了社会的黑暗与腐朽，谴责了统治阶级的残暴与奸诈，" +
                "反映了人民在动乱时代的苦难和明君仁政的愿望。小说也反映了作者对农民起义的偏见，以及因果报应和宿命论等思想。战争描写是《三国演义》突出的艺术成就。" +
                "这部小说通过惊心动魄的军事、政治斗争，运用夸张、对比、烘托、渲染等艺术手法，成功地塑造了诸葛亮、曹操、关羽、张飞等一批鲜明、生动的人物形象。" +
                "《三国演义》结构宏伟而又严密精巧，语言简洁、明快、生动。有的评论认为这部作品在艺术上的不足之处是人物性格缺乏发展变化，有的人物渲染夸张过分导致失真。" +
                "《三国演义》标志着历史演义小说的辉煌成就。在传播政治、军事斗争经验、推动历史演义创作的繁荣等方面都起过积极作用。" +
                "《三国演义》的版本主要有明嘉靖刻本《三国志通俗演义》和清毛宗岗增删评点的《三国志演义》";
        //摘要，200是摘要的最大长度
        String summary = HanLP.getSummary(text, 50);
        System.out.println("summary = " + summary);
    }
}