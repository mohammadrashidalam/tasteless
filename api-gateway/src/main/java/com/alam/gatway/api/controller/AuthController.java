package com.alam.gatway.api.controller;

import com.alam.gatway.api.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    @GetMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user,
            Model model) {
        log.info("User {} signed in with authorities: {}", user.getEmail(), user.getAuthorities());
        //Creating AuthResponse with user details and tokens
        assert client.getRefreshToken() != null;
        assert client.getAccessToken().getExpiresAt() != null;
       List<String> authorities= user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();
        AuthResponse authResponse=AuthResponse.builder()
                .userId(user.getEmail())
                .accessToken(client.getAccessToken().getTokenValue())
                .refreshToken(client.getRefreshToken().getTokenValue())
                .expireAt(client.getAccessToken().getExpiresAt().getEpochSecond())
                .authorities(authorities)
                .build();
        return ResponseEntity.ok(authResponse);

    }
}
