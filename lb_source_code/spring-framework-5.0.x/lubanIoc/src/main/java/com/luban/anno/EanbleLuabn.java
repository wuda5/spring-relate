package com.luban.anno;

//import com.luban.imports.MyImportSelector;
import com.luban.imports.MyImportSelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
//@ComponentScan("com.luban.dao")
@Retention(RetentionPolicy.RUNTIME)
@Import(MyImportSelector.class)
public @interface EanbleLuabn {

	 boolean value();
}
