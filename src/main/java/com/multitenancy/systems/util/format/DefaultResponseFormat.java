package com.multitenancy.systems.util.format;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"isSuccess", "message"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultResponseFormat {
    @JsonProperty("isSuccess")
    private Boolean success;
    private String message;
    private String errorId;
    private String id;

    public DefaultResponseFormat() {}

    public DefaultResponseFormat(Boolean success, String message, String errorId, String id) {
        this.success = success;
        this.message = message;
        this.errorId = errorId;
        this.id = id;
    }

    public DefaultResponseFormat(Boolean success, String message, String id) {
        this.success = success;
        this.message = message;
        this.id = id;
    }

    public DefaultResponseFormat(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
