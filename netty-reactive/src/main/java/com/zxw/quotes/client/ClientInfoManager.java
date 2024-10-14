package com.zxw.quotes.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxw
 * @date 2024-05-23 10:04
 */
@Component
@Slf4j
public class ClientInfoManager {

    Map<String, ClientInfo> clientMap = new ConcurrentHashMap<>();

    public void addClient(ClientInfo clientInfo) {
        ClientInfo existClient = clientMap.get(clientInfo.getName());
        if (existClient != null) {
            log.info("client信息已存在:{}", clientInfo);
        }
        clientMap.put(clientInfo.getName(), clientInfo);
    }

    public Map<String, ClientInfo> findAll(){
        return clientMap;
    }

    public void removeClient(String clientName) {
        clientMap.remove(clientName);
    }
}
