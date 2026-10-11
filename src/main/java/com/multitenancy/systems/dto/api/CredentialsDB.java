package com.multitenancy.systems.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDB {
    private String id;
    private String appName;
    private String dbName;
    private String dbType;
    private String jdbcUrl;
    private String username;
    private String password;
    private Boolean isActive;

    public CredentialsDB(){}

}
