package com.multitenancy.systems.util.format;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonPropertyOrder({"page", "size", "totalPage", "totalData", "nextPage", "prevPage"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pages {
    private Integer page;
    private Integer size;
    private Long totalPage;
    private Long totalData;
    private String nextPage;
    private String prevPage;

    public Pages() {}

    public Pages(Integer page, Integer size, Long totalPage, Long totalData, String nextPage, String prevPage) {
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
        this.totalData = totalData;
        this.nextPage = nextPage;
        this.prevPage = prevPage;
    }
}
