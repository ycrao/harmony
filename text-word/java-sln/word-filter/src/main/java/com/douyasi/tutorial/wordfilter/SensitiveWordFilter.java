package com.douyasi.tutorial.wordfilter;

import java.util.Collection;
import java.util.List;

/**
 * SensitiveWordFilter
 *
 * @author raoyc
 */
public interface SensitiveWordFilter {

    /**
     * 是否命中关键词
     *
     * @param text 文本内容
     * @return 命中则返回 true
     */
    boolean isMatch(String text);

    /**
     * 构建敏感关键词索引
     *
     * @param keywords 词库列表
     */
    void build(Collection<String> keywords);

    /**
     * 清空
     */
    void clear();

    /**
     * 遍历
     *
     * @param text 文本内容
     * @param processor 命中文本处理器
     */
    void walking(String text, HitText processor);

    /**
     * 找出所有命中关键词
     *
     * @param text 文本
     * @return 关键词列表
     */
    List<String> findAll(String text);

    interface HitText {
        /**
         * 命中文本
         *
         * @param begin 字符开始位置
         * @param end   字符结束位置
         * @param value 命中词
         * @param index 命中索引
         * @return 是否继续匹配，true表示继续；否则表示中断
         */
        boolean hit(int begin, int end, String value, int index);
    }
}
