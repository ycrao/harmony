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
        return new TrieSensitiveWordFilter();
    }
}
