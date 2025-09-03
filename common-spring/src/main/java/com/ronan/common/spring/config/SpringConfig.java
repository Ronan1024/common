package com.ronan.common.spring.config;

import com.ronan.common.spring.core.SpringApplicationUtil;
import org.springframework.context.annotation.Bean;

/**
 * @program: common
 * @description:
 * @author: L.J.Ran
 * @create: 2025/9/2
 */
public class SpringConfig {

    @Bean
    public SpringApplicationUtil springApplicationUtil(){
        return new SpringApplicationUtil();
    }
}
