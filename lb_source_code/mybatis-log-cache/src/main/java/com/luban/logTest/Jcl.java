package com.luban.logTest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Jcl {
    public static void main(String[] args) {

        Log log = LogFactory.getLog("jcl");
        log.info("xxxx --- jcl ---打印白色?--log4j-");
    }
}
