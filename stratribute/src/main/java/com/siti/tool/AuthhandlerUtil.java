package com.siti.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangt
 */

public class AuthhandlerUtil {
	
	/**
	 * 将增删改查权限移动到一个Map中,前端需要
	 * @param auth
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void adjustCRUDAuth(Map auth) {
		Map authMap = new HashMap();
		authMap.put("id", auth.get("orgAuthRelaId"));
		auth.remove("orgAuthRelaId");
		authMap.put("createAuth", auth.get("createAuth"));
		auth.remove("createAuth");
		authMap.put("readAuth", auth.get("readAuth"));
		auth.remove("readAuth");
		authMap.put("updateAuth", auth.get("updateAuth"));
		auth.remove("updateAuth");
		authMap.put("deleteAuth", auth.get("deleteAuth"));
		auth.remove("deleteAuth");
		auth.put("auth", authMap);
	}
}
