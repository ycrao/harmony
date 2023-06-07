package com.douyasi.tutorial.wordfilter.impl;

import com.douyasi.tutorial.wordfilter.SensitiveWordFilter;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * TrieSensitiveWordFilter
 * ref: <a href="https://github.com/AnthonyZero/text-processor">AnthonyZero/text-processor</a>
 *
 * @author raoyc
 */
public class TrieSensitiveWordFilter implements SensitiveWordFilter {

    /**
     * 纯英文字母与数字组合正则模式
     */
    private static final Pattern PATTERN = Pattern.compile("[a-z1-9]+", Pattern.CASE_INSENSITIVE);
    /**
     * 并发锁
     */
    private static final CountDownLatch INIT_LOCK = new CountDownLatch(1);

    private final AtomicReference<AhoCorasickDoubleArrayTrie<String>> prefix = new AtomicReference<>();
    private final AtomicReference<AhoCorasickDoubleArrayTrie<String>> exact = new AtomicReference<>();

    @Override
    public boolean isMatch(String text) {
        try {
            INIT_LOCK.await();
            if (!StringUtils.hasText(text)) {
                // 如果非字符串
                return true;
            }
            // 转为全小写
            String txt = text.toLowerCase(Locale.ROOT).trim();
            if (!exactMatch(txt)) {
                return prefix.get() != null && prefix.get().matches(txt);
            }
            return true;
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    @Override
    public synchronized void build(Collection<String> keywords) {
        if (keywords == null) {
            return;
        }
        // ac 前缀树 存放所有词汇
        AhoCorasickDoubleArrayTrie<String> localPrefix = new AhoCorasickDoubleArrayTrie<>();
        // ac 前缀树 存放英文词汇
        AhoCorasickDoubleArrayTrie<String> localExact = new AhoCorasickDoubleArrayTrie<>();
        Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        Map<String, String> words = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String key : keywords) {
            if (!StringUtils.hasText(key)) {
                continue;
            }
            String tKey = key.toLowerCase(Locale.ROOT).trim();
            // 单独匹配英文单词，避免命中词缀
            if (Pattern.matches("^[a-z]+$", tKey)) {
                words.put(tKey, key);
            } else {
                map.put(tKey, key);
            }
        }
        if (!map.isEmpty()) {
            localPrefix.build(map);
            prefix.set(localPrefix);
        } else {
            prefix.set(null);
        }
        if (!words.isEmpty()) {
            localExact.build(words);
            exact.set(localExact);
        } else {
            exact.set(null);
        }
        if (INIT_LOCK.getCount() > 0) {
            INIT_LOCK.countDown();
        }
    }

    @Override
    public void clear() {
        prefix.set(null);
        exact.set(null);
    }

    @Override
    public void walking(String text, HitText processor) {
        AtomicInteger count = new AtomicInteger(0);
        if (exact.get() != null) {
            AhoCorasickDoubleArrayTrie<String> index = exact.get();
            Matcher matcher = PATTERN.matcher(text);
            while (matcher.find()) {
                String word = matcher.group(0);
                if (index.exactMatchSearch(word) >= 0) {
                    processor.hit(matcher.regionStart(), matcher.regionEnd(), word, count.getAndIncrement());
                }
            }
        }
        if (prefix.get() != null) {
            prefix.get().parseText(text, (begin, end, value) -> {
                return processor.hit(begin, end, value, count.getAndIncrement());
            });
        }
    }

    @Override
    public List<String> findAll(String text) {
        List<String> all = new ArrayList<>();
        if (!StringUtils.hasText(text)) {
            return all;
        }
        walking(text, (begin, end, val, idx) -> {
            all.add(val);
            return true;
        });
        return all;
    }


    private boolean exactMatch(String text) {
        AhoCorasickDoubleArrayTrie<String> index = exact.get();
        if (index == null) {
            return false;
        }
        Matcher matcher = PATTERN.matcher(text);
        while (matcher.find()) {
            String word = matcher.group(0);
            if (index.exactMatchSearch(word) >= 0) {
                return true;
            }
        }
        return false;
    }
}
