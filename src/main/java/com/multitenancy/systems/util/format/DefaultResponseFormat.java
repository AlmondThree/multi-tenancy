package com.multitenancy.systems.util.format;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"isSuccess", "message"})
public class DefaultResponseFormat {
    @JsonProperty("isSuccess")
    private Boolean success;
    private String message;

}
