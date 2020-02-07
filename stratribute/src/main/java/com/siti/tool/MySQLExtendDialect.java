package com.siti.tool;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySQLExtendDialect extends MySQLDialect {
	public MySQLExtendDialect(){
        super();
        registerFunction("convert_gbk",
                new SQLFunctionTemplate(StandardBasicTypes.STRING, "convert(?1 using gbk)") );
    }
}
