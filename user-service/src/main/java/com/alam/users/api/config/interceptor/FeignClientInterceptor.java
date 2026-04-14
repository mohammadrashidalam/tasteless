package com.alam.users.api.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.util.Objects;


@Configuration
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {
    private OAuth2AuthorizedClientManager manager;

    public FeignClientInterceptor(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("FeignClientInterceptor :: apply");
        String token = Objects.requireNonNull(manager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("my-internal-client")
                .principal("internal").build())).getAccessToken().getTokenValue();
        log.info("Token - {}",token);
        requestTemplate.header("Authorization","Bearer " + token);
    }
}
