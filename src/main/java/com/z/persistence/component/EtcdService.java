package com.z.persistence.component;

import com.z.persistence.exceptions.CustomEtcdException;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.DeleteOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * EtcdService
 *
 * @author Lee
 * @date 2023/5/14
 */
@Component
@Slf4j
public class EtcdService {


    @Resource
    private Client etcdClient;


    public boolean put (String key, String value) {
        try {
            ByteSequence etcdKey = ByteSequence.from(key, StandardCharsets.UTF_8);
            ByteSequence etcdValue = ByteSequence.from(value, StandardCharsets.UTF_8);
            CompletableFuture<PutResponse> put = etcdClient.getKVClient().put(etcdKey, etcdValue);
            put.get();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
            return false;
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            return false;
        }
        return true;
    }

    public List<KeyValue> get(String key) {
        try {
            ByteSequence etcdKey = ByteSequence.from(key, StandardCharsets.UTF_8);
            CompletableFuture<GetResponse> getResponseCompletableFuture = etcdClient.getKVClient().get(etcdKey);
            GetResponse getResponse = getResponseCompletableFuture.get();
            List<KeyValue> kvs = getResponse.getKvs();
            return kvs;
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            throw new CustomEtcdException(exp.getMessage(), exp);
        }
    }

    public boolean delete(String key) {
        try {
            ByteSequence etcdKey = ByteSequence.from(key, StandardCharsets.UTF_8);
            DeleteOption deleteOption = DeleteOption.DEFAULT;
            CompletableFuture<DeleteResponse> deleteFuture = etcdClient.getKVClient().delete(etcdKey, deleteOption);
            DeleteResponse deleteResponse = deleteFuture.get();
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            return false;
        }
        return true;
    }

    public boolean update(String key, String newValue) {
        try {
            ByteSequence etcdKey = ByteSequence.from(key, StandardCharsets.UTF_8);
            ByteSequence etcdNewValue = ByteSequence.from(newValue, StandardCharsets.UTF_8);
            // 更新键的值
            etcdClient.getKVClient().put(etcdKey, etcdNewValue).get();
        } catch (Exception exp) {
            log.error(exp.getMessage(), exp);
            return false;
        }
        return true;
    }

}
