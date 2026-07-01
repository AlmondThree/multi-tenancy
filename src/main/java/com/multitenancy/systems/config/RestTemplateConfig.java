package com.multitenancy.systems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "restAsyncExecutor")
    public Executor restAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);       // Jumlah minimum thread aktif
        executor.setMaxPoolSize(50);        // Jumlah maksimum thread jika antrean penuh
        executor.setQueueCapacity(500);     // Batas antrean penampung tugas log
        executor.setThreadNamePrefix("LogAsync");
        executor.initialize();
        return executor;
    }
}
