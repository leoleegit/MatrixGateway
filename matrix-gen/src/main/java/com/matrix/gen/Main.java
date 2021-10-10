package com.matrix.gen;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class Main {
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/matrix_media?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
    public static final String DB_DRIVER   = "com.mysql.cj.jdbc.Driver";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "xxxxxx";

    public static final String AUTHOR = "[matrix generator]";

    public static  String[] TABLE_NAMES = null;
    public static  String   TABLE_PREFIX=null;

    public static String projectPath = System.getProperty("user.dir");
    public static String moduleName  = "";


    public static void main(String[] args) {
        TABLE_NAMES = new String[]{"media"};
        TABLE_PREFIX="t_";

        moduleName = "matrix-service";
        gen();
    }

    public static PackageConfig defaultPackageConfig(){
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.matrix.service");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setServiceImpl("impl");
        pc.setXml("mapper");

        //pc.setService("delete_delete");
        //pc.setServiceImpl("service");
        return pc;
    }

    public static GlobalConfig defaultGlobalConfig(){
        String outPutDir = projectPath;
        if(!"".equals(moduleName)){
            outPutDir = StrUtil.format("{}/{}/src/main/java",projectPath,moduleName);
        }else{
            outPutDir = StrUtil.format("{}/src/main/java",projectPath);
        }
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outPutDir);
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);

        gc.setBaseResultMap(true);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setServiceImplName("%sServiceImpl");
        return gc;
    }


    public static void gen(){
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(defaultGlobalConfig());
        autoGenerator.setDataSource(defaultDataSourceConfig());
        autoGenerator.setPackageInfo(defaultPackageConfig());
        autoGenerator.setStrategy(defaultStrategyConfig());
        autoGenerator.setTemplate(defaultTemplateConfig());
        autoGenerator.execute();
    }

    public static DataSourceConfig defaultDataSourceConfig(){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        dsc.setDriverName(DB_DRIVER);
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);

        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//                if (fieldType.toLowerCase().contains("tinyint")) {
//                    return DbColumnType.BYTE;
//                }
                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }

        });
        return dsc;
    }

    public static StrategyConfig defaultStrategyConfig(){
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);

        strategy.setInclude(TABLE_NAMES);
        strategy.setTablePrefix(TABLE_PREFIX);
        return strategy;
    }

    public static TemplateConfig defaultTemplateConfig(){
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setService(null);
        return templateConfig;
    }
}
