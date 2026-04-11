package com.alam.users.api.external.factory;

import com.alam.users.api.external.commons.ExternalServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class ExternalServicesFactory {
    private final Map<String, ExternalServiceClient> serviceClients;

    public ExternalServiceClient getServiceClient(String clientType) {
        ExternalServiceClient client = serviceClients.get(clientType);
        if (client == null) {
            throw new IllegalArgumentException("Invalid client type: " + clientType);
        }
        return client;
    }
}
