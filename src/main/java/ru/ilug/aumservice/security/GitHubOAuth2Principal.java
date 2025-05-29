package ru.ilug.aumservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import ru.ilug.aumservice.data.model.GitHubTokenInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public record GitHubOAuth2Principal(GitHubTokenInfo tokenInfo) implements OAuth2AuthenticatedPrincipal {

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "user_login", tokenInfo.getUser().getLogin(),
                "scopes", tokenInfo.getScopes()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return tokenInfo.getUser().getLogin();
    }
}
