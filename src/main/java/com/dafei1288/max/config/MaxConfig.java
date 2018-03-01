package com.dafei1288.max.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 配置类
 * 默认加载类入境下 max.yaml
 * 可以通过配置环境变量max.maxConfigFilePath 来修改变量表文件
 *
 * */
public class MaxConfig {
    private static final Logger logger = LoggerFactory.getLogger(MaxConfig.class);
    private static MaxConfig maxConfig;
    private MaxConfigVO mcv;
    public static String maxConfigFilePath = "/max.yaml";
    static{
        String temp = System.getenv("max.maxConfigFilePath");
        if(temp!=null && !"".equals(temp)){
            maxConfigFilePath = temp;
        }
    }


    private MaxConfig() throws IOException {
        Yaml yaml = new Yaml();
        Path p = Paths.get(maxConfigFilePath);
        InputStream is = null;
        if(Files.exists(p)){
            logger.debug("load max config file with path : ["+maxConfigFilePath+"]");
            is = new FileInputStream(maxConfigFilePath);
        }else{
            logger.debug("default config did not exits !!!");
            is = MaxConfig.class.getResourceAsStream(maxConfigFilePath);

        }
        mcv = yaml.loadAs(is,MaxConfigVO.class);
        is.close();
    }
    public static MaxConfig getInstance() throws IOException {
        if(maxConfig==null){
            synchronized (MaxConfig.class){
                maxConfig = new MaxConfig();
            }
        }
        return maxConfig;
    }


    public String getStringComparatorRule(){
        return mcv.getMax().get("stringComparatorRule");
    }

    public static class MaxConfigVO{
        private Map<String,String> max;

        public Map<String, String> getMax() {
            return max;
        }

        public void setMax(Map<String, String> max) {
            this.max = max;
        }
    }
}
