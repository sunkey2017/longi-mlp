package com.longi.mlp.codegen;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: Date: 2019-03-12 09:01
 *
 * @author liyi
 */

@Slf4j
public class CodeGenerator {
    private static final String CONFIG_FILE = "mybatis-codegen-config.properties";
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String PACKAGE = "com.longi.ai";
    private static final String OUTPUT_PATH = PROJECT_PATH + "/mlp-common/mlp-common-codegen/target/generated-sources/mybatis";

    public static void main(String[] args) {
        CodeGenConfig codeGenConfig = parseConfig();
        if (codeGenConfig == null) {
            return;
        }
        DataSourceConfig dataSourceConfig = buildDataSource(codeGenConfig);
        StrategyConfig strategy = buildStrategyConfig(codeGenConfig);
        GlobalConfig globalConfig = buildGlobalConfig(codeGenConfig);
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(PACKAGE);
        packageConfig.setModuleName(codeGenConfig.getModuleName());
        InjectionConfig injectionConfig = initInjectionConfig(codeGenConfig);
        TemplateConfig templateConfig = new TemplateConfig()
                .setServiceImpl("templates/serviceImpl.java")
                .setController(null);
        AutoGenerator autoGenerator = new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setPackageInfo(packageConfig)
                .setTemplate(templateConfig.setXml(null))
                .setStrategy(strategy)
                .setCfg(injectionConfig)
                .setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    private static InjectionConfig initInjectionConfig(CodeGenConfig config) {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap()  {
            }
        };
        injectionConfig.setFileOutConfigList(Collections.singletonList(new FileOutConfig(
                "/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return OUTPUT_PATH + "/src/main/resources/mapper/" + config.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        }));
        return injectionConfig;
    }

    private static CodeGenConfig parseConfig() {
        try (InputStream inStream = CodeGenerator.class.getResourceAsStream("/" + CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(inStream);
            CodeGenConfig codeGenConfig = new CodeGenConfig();
            codeGenConfig.setDbIp(props.getProperty("db.ip"));
            codeGenConfig.setDbPort(props.getProperty("db.port"));
            codeGenConfig.setDbName(props.getProperty("db.name"));
            codeGenConfig.setDbUser(props.getProperty("db.user"));
            codeGenConfig.setDbPassword(props.getProperty("db.password"));
            codeGenConfig.setModuleName(props.getProperty("module.name"));
            codeGenConfig.setTables(props.getProperty("tables"));
            codeGenConfig.setDevAuthor(props.getProperty("dev.author"));
            codeGenConfig.setTablePrefix(props.getProperty("table.prefix"));
            return codeGenConfig;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    private static GlobalConfig buildGlobalConfig(CodeGenConfig codeGenConfig) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(OUTPUT_PATH + "/src/main/java")
                .setActiveRecord(true)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setAuthor(codeGenConfig.getDevAuthor())
                .setOpen(false)
                .setXmlName("%sMapper")
                .setMapperName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setIdType(IdType.ID_WORKER_STR)
                .setDateType(DateType.TIME_PACK)
                .setSwagger2(false);
        return globalConfig;
    }

    private static StrategyConfig buildStrategyConfig(CodeGenConfig codeGenConfig) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setCapitalMode(true)
                .setTablePrefix("ai_")
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setSuperEntityClass("com.asiainfo.mlp.common.core.base.BaseEntity")
                .setEntityLombokModel(true)
                .setEntityBuilderModel(true)
                .setRestControllerStyle(true)
                .setInclude(codeGenConfig.getTables())
                .setEntityTableFieldAnnotationEnable(true)
                .setControllerMappingHyphenStyle(true);
        if (StringUtils.isNotBlank(codeGenConfig.getTablePrefix())) {
            strategy.setTablePrefix(codeGenConfig.getTablePrefix());
        }
        return strategy;
    }

    private static DataSourceConfig buildDataSource(CodeGenConfig config) {
        String dbUrl = "jdbc:mysql://" + config.getDbIp() + ":" + config.getDbPort() + "/"
                + config.getDbName() + "?useUnicode=true&useSSL=false&characterEncoding=utf8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUrl(dbUrl);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername(config.getDbUser());
        dataSourceConfig.setPassword(config.getDbPassword());
        return dataSourceConfig;
    }
}
