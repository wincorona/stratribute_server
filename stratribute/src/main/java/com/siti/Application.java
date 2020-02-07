package com.siti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhangt
 */

/**
 * 程序启动入口
 * @author
 *
 */
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.siti.common.*.mapper")
public class Application  extends SpringBootServletInitializer {
}



 
  
 


