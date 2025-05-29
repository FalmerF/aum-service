package ru.ilug.aumservice.data.model;

import lombok.Data;

@Data
public class GitHubTokenInfo {

    private User user;
    private String[] scopes;

}
