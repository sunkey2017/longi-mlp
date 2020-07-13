package com.longi.mlp.codegen;

import lombok.Data;

@Data
public class CodeGenConfig {
    private String dbIp;
    private String dbPort;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    private String tables;
    private String moduleName;
    private String devAuthor;
    private String tablePrefix;
}
