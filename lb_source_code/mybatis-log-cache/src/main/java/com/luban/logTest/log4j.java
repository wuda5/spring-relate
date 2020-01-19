package com.luban.logTest;



import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.util.Properties;

public class log4j {
    static final Logger log = Logger.getLogger(log4j.class);
    public static void main(String[] args) {


        log.info("xxxx --- hhh ---");

    }
}
