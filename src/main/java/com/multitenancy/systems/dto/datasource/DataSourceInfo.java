package com.multitenancy.systems.dto.datasource;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;

@Getter
@Setter
public class DataSourceInfo {
    private String databaseName;
    private DataSource dataSource;

    public DataSourceInfo(String databaseName, DataSource dataSource) {
        this.databaseName = databaseName;
        this.dataSource = dataSource;
    }
}
