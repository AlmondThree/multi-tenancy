package com.multitenancy.systems.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListCredentialsResponse {
    private Boolean status;
    private String message;
    private CredentialsDB[] data;
}
