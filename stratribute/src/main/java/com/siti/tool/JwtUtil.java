package com.siti.tool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author zhangt
 */

public class JwtUtil {
	/**
	 * token过期时间
	 */
	public static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
	
	/**
	 * //自定义错误码： 解析错误
	 */
	public static final Integer PARSE_FAILURE = 600;
	
	/**
	 * 自定义错误码： 过期
	 */
	public static final Integer TIME_EXPIRE = 601;
	
	/**
	 * //自定义错误码： 无token
	 */
	public static final Integer NO_TOKEN = 602;
	
	/**
	 * token密码
	 */
	private static final String TOKEN_SECRET = "sueii2019jiaxing";
	
	/**
	 * 生成token
	 * @param userName
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt(String userName, Integer userId) throws Exception{
		//过期时间
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		//密钥及加密算法
		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
		//设置头部信息
		Map<String,Object> header = new HashMap<>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		//附带username,生成签名
		String token = JWT.create()
				.withHeader(header)
				.withClaim("userName", userName)
				.withClaim("userId", userId)
				.withExpiresAt(date)
				.sign(algorithm);
		return token;
	}
	
	/**
	 * token解码
	 * @param token
	 * @return
	 */
	public static DecodedJWT decrypt(String token) throws Exception{
		DecodedJWT jwt = null;
    	Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        jwt = verifier.verify(token);
        return jwt;
	}
}
