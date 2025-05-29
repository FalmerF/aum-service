package ru.ilug.aumservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.ilug.aumservice.data.model.GitHubTokenInfo;

import java.util.Map;

@Slf4j
@Component
public class GitHubIntrospector implements OpaqueTokenIntrospector {

    private final WebClient webClient;

    public GitHubIntrospector(@Value("${application.oauth2.client-id}") String clientId,
                              @Value("${application.oauth2.client-secret}") String clientSecret) {
        webClient = WebClient.builder()
                .baseUrl("https://api.github.com/applications/" + clientId)
                .defaultHeaders(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setBasicAuth(clientId, clientSecret);
                }).build();
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        GitHubTokenInfo tokenInfo = webClient.post()
                .uri("/token")
                .bodyValue(Map.of("access_token", token))
                .retrieve()
                .bodyToMono(GitHubTokenInfo.class)
                .block();

        return new GitHubOAuth2Principal(tokenInfo);
    }
}
