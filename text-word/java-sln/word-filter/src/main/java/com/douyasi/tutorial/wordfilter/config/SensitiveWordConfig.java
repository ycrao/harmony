package com.douyasi.tutorial.wordfilter.config;

import com.douyasi.tutorial.wordfilter.SensitiveWordFilter;
import com.douyasi.tutorial.wordfilter.impl.TrieSensitiveWordFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

/**
 * SensitiveWordConfig
 *
 * @author raoyc
 */
@Configuration
public class SensitiveWordConfig {

    @Lazy
    @Primary
    @Bean(destroyMethod = "clear")
    public SensitiveWordFilter trieSensitiveWordFilter() {
        // TODO 可以做一些初始化词库的动作，参考测试用例代码
        return new TrieSensitiveWordFilter();
    }
}
