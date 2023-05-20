package com.z.persistence.config;

import io.etcd.jetcd.*;
import io.etcd.jetcd.watch.WatchEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * EtcdConfiguration
 *
 * @author Lee
 * @date 2023/5/14
 */
@Configuration
@Slf4j
public class EtcdConfiguration {

    /**
     * 返回etcd相关
     * @return
     */
    @Bean
    public Client EtcdClient() {
        Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();
        return client;
    }


    @Bean
    public Watch.Listener listener () {
        String key = "service1";
        Watch.Listener listener = Watch.listener(watchResponse -> {
            log.info("收到{}的事件", key);
            watchResponse.getEvents().forEach(watchEvent -> {
                WatchEvent.EventType eventType = watchEvent.getEventType();
                KeyValue keyValue = watchEvent.getKeyValue();
                log.info(
                        "event type is {}. key is {}, value is {}",
                        eventType,
                        keyValue.getKey().toString(StandardCharsets.UTF_8),
                        keyValue.getValue().toString(StandardCharsets.UTF_8)
                        );
            });
        });
        return listener;
    }

    @Bean
    public Watch.Watcher watcher(Watch.Listener listener) {
        String key = "service1";
        return EtcdClient().getWatchClient().watch(ByteSequence.from(key, StandardCharsets.UTF_8), listener);
    }
}
