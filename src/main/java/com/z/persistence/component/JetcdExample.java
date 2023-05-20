package com.z.persistence.component;

/**
 * JetcdExample
 *
 * @author Lee
 * @date 2023/5/20
 */
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.api.DeleteRangeRequest;
import io.etcd.jetcd.api.PutRequest;
import io.etcd.jetcd.api.RangeRequest;
import io.etcd.jetcd.api.RangeResponse;
import io.etcd.jetcd.api.ResponseHeader;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.DeleteOption;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class JetcdExample {
    public static void main(String[] args) {
        // 创建etcd客户端连接
        Client client = Client.builder().endpoints("http://127.0.0.1:2379").build();

        // 创建kv客户端
        KV kvClient = client.getKVClient();
        try {
            // 设置键值对
            ByteSequence key = ByteSequence.from("service1/message", StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from("Hello world", StandardCharsets.UTF_8);
            kvClient.put(key, value).get();

            // 获取键的值
            GetOption getOption = GetOption.DEFAULT;
            CompletableFuture<GetResponse> getFuture = kvClient.get(key, getOption);
            GetResponse getResponse = getFuture.get();

//            RangeResponse getResponse = getFuture.get();
            String retrievedValue = getResponse.getKvs().get(0).getValue().toString(StandardCharsets.UTF_8);
            System.out.println("Retrieved value: " + retrievedValue);

            // 更新键的值
            ByteSequence newValue = ByteSequence.from("Updated value", StandardCharsets.UTF_8);
            kvClient.put(key, newValue).get();

            // 删除键
            DeleteOption deleteOption = DeleteOption.DEFAULT;
            CompletableFuture<DeleteResponse> deleteFuture = kvClient.delete(key, deleteOption);
            DeleteResponse deleteResponse = deleteFuture.get();
//            CompletableFuture<io.etcd.jetcd.api.DeleteRangeResponse> deleteFuture = kvClient.delete(key, deleteOption);
//            io.etcd.jetcd.api.DeleteRangeResponse deleteResponse = deleteFuture.get();
            long deleted = deleteResponse.getDeleted();

            System.out.println("Deleted keys: " + deleted);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 关闭etcd客户端连接
        client.close();
    }
}
