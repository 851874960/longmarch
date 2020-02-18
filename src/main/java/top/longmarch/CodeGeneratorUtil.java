package top.longmarch;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CodeGeneratorUtil {

    private String projectPath = System.getProperty("user.dir");

    public static void main(String[] args) {
        CodeGeneratorUtil codeGenerator = new CodeGeneratorUtil();
//        codeGenerator.run("sys2", Arrays.asList("sys_user","sys_role","sys_permission","sys_user_role_rel","sys_role_permission_rel","sys_dictionary", "sys_login_log", "sys_operate_log", "sys_parameter"));
//        codeGenerator.run("cms", Arrays.asList("cms_article","cms_category"));
//        codeGenerator.run("sys", Arrays.asList("sys_department_user_rel"));
        codeGenerator.run("test", Arrays.asList("test_member"));
    }

    public void run(String moduleName, List<String> tableNameList) {
        for (String tableName : tableNameList) {
            buildAutoGenerator(moduleName, tableName, buildFieldGenerationConditionList(tableName)).execute();
        }
    }

    public List<Map<String, Object>> buildFieldGenerationConditionList(String tableName) {
        List<Map<String, Object>> fieldGenerationConditionList = new ArrayList<>();

        Map<String, Object> fieldGenerationCondition = new HashMap<>();
        fieldGenerationCondition.put("tableName", tableName);
        fieldGenerationCondition.put("propertyName", "id");
        fieldGenerationCondition.put("remark", "ID");
        fieldGenerationCondition.put("notNull", false);
        fieldGenerationCondition.put("listShow", true);
        fieldGenerationCondition.put("formShow", false);
        fieldGenerationCondition.put("formType", "null");
        fieldGenerationCondition.put("queryType", null);
        fieldGenerationCondition.put("orderBy", true);
        fieldGenerationCondition.put("parameter", true);
        fieldGenerationCondition.put("defaultValue", "null");
        fieldGenerationCondition.put("dictCode", null);
        fieldGenerationConditionList.add(fieldGenerationCondition);

        fieldGenerationCondition = new HashMap<>();
        fieldGenerationCondition.put("tableName", tableName);
        fieldGenerationCondition.put("propertyName", "name");
        fieldGenerationCondition.put("remark", "用户名");
        fieldGenerationCondition.put("notNull", true);
        fieldGenerationCondition.put("listShow", true);
        fieldGenerationCondition.put("formShow", true);
        fieldGenerationCondition.put("formType", "input");
        fieldGenerationCondition.put("queryType", "like");
        fieldGenerationCondition.put("orderBy", false);
        fieldGenerationCondition.put("parameter", true);
        fieldGenerationCondition.put("defaultValue", "null");
        fieldGenerationCondition.put("dictCode", null);
        fieldGenerationConditionList.add(fieldGenerationCondition);

        fieldGenerationCondition = new HashMap<>();
        fieldGenerationCondition.put("tableName", tableName);
        fieldGenerationCondition.put("propertyName", "sex");
        fieldGenerationCondition.put("remark", "性别");
        fieldGenerationCondition.put("notNull", true);
        fieldGenerationCondition.put("listShow", true);
        fieldGenerationCondition.put("formShow", true);
        fieldGenerationCondition.put("formType", "input");
        fieldGenerationCondition.put("queryType", "eq");
        fieldGenerationCondition.put("orderBy", false);
        fieldGenerationCondition.put("parameter", true);
        fieldGenerationCondition.put("defaultValue", 1);
        fieldGenerationCondition.put("dictCode", "sex_dict");
        fieldGenerationConditionList.add(fieldGenerationCondition);

        fieldGenerationCondition = new HashMap<>();
        fieldGenerationCondition.put("tableName", tableName);
        fieldGenerationCondition.put("propertyName", "status");
        fieldGenerationCondition.put("remark", "用户状态");
        fieldGenerationCondition.put("notNull", true);
        fieldGenerationCondition.put("listShow", true);
        fieldGenerationCondition.put("formShow", true);
        fieldGenerationCondition.put("formType", "select");
        fieldGenerationCondition.put("queryType", "eq");
        fieldGenerationCondition.put("orderBy", false);
        fieldGenerationCondition.put("parameter", true);
        fieldGenerationCondition.put("defaultValue", 1);
        fieldGenerationCondition.put("dictCode", "status_dict");
        fieldGenerationConditionList.add(fieldGenerationCondition);

        fieldGenerationCondition = new HashMap<>();
        fieldGenerationCondition.put("tableName", tableName);
        fieldGenerationCondition.put("propertyName", "createTime");
        fieldGenerationCondition.put("remark", "创建日期");
        fieldGenerationCondition.put("notNull", true);
        fieldGenerationCondition.put("listShow", true);
        fieldGenerationCondition.put("formShow", true);
        fieldGenerationCondition.put("formType", "date");
        fieldGenerationCondition.put("queryType", "date");
        fieldGenerationCondition.put("orderBy", false);
        fieldGenerationCondition.put("parameter", true);
        fieldGenerationCondition.put("defaultValue", "null");
        fieldGenerationCondition.put("dictCode", null);
        fieldGenerationConditionList.add(fieldGenerationCondition);

        return fieldGenerationConditionList;
    }

    private AutoGenerator buildAutoGenerator(String moduleName, String tableName, List<Map<String, Object>> fieldGenerationConditionList) {
        AutoGenerator autoGenerator = new AutoGenerator();
        GlobalConfig globalConfig = buildGlobalConfig();
        TemplateConfig templateConfig = buildTemplateConfig();
        DataSourceConfig dataSourceConfig = buildDataSourceConfig();
        PackageConfig packageConfig = buildPackageConfig(moduleName);
        InjectionConfig injectionConfig = buildInjectionConfig(packageConfig);
        StrategyConfig strategy = buildStrategyConfig(packageConfig, tableName);

        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.setStrategy(strategy);
        autoGenerator.setTemplateEngine(new LmFreemarkerTemplateEngine(fieldGenerationConditionList));

        return autoGenerator;
    }

    private TemplateConfig buildTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity("/templates/entity.java");
        templateConfig.setController("/templates/controller.java");
        return templateConfig;
    }

    private StrategyConfig buildStrategyConfig(PackageConfig packageConfig, String... tableNames) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setLogicDeleteFieldName("is_delete");
        strategy.setTableFillList(Arrays.asList(
                new TableFill("create_by", FieldFill.INSERT),
                new TableFill("create_time", FieldFill.INSERT),
                new TableFill("update_by", FieldFill.UPDATE),
                new TableFill("update_time", FieldFill.UPDATE)));
        strategy.setRestControllerStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setInclude(tableNames);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        return strategy;
    }

    private InjectionConfig buildInjectionConfig(PackageConfig packageConfig) {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();
        fileOutConfigList.add(new FileOutConfig("/templates/index.vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/page/index.vue";
            }
        });
        fileOutConfigList.add(new FileOutConfig("/templates/api.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/page/"+tableInfo.getEntityName()+"Api.js";
            }
        });
        fileOutConfigList.add(new FileOutConfig("/templates/lang_zh.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/page/lang_zh.js";
            }
        });
        fileOutConfigList.add(new FileOutConfig("/templates/lang_en.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/page/lang_en.js";
            }
        });
        fileOutConfigList.add(new FileOutConfig("/templates/longmarch.sql.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/page/longmarch.sql";
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigList);
        return injectionConfig;
    }

    private PackageConfig buildPackageConfig(String moduleName) {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(moduleName);
        packageConfig.setParent("top.longmarch");
        packageConfig.setMapper("dao");
        return packageConfig;
    }

    private DataSourceConfig buildDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://122.51.244.159:3306/longmarch?useUnicode=true&characterEncoding=utf8&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("longmarch");
        dataSourceConfig.setPassword("pfbNKZmkcZ7CDhSf");
        return dataSourceConfig;
    }

    private GlobalConfig buildGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("YuYue");
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);
        globalConfig.setSwagger2(true);
        globalConfig.setMapperName("%sDao");
        globalConfig.setDateType(DateType.ONLY_DATE);
        return globalConfig;
    }

}
