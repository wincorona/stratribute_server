package com.siti;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.siti.tool.CommonI18Constant;
import com.siti.tool.JwtUtil;
import com.siti.tool.MessageUtils;

/**
 * @author zhangt
 */

@Component
@WebFilter(filterName = "otherFilter")
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse rep = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        rep.setHeader("Access-Control-Allow-Origin", "*");
        rep.setHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, content-Type, Accept, Authorization");
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        //response.setHeader("Access-Control-Max-Age", "3600");
        
        //反注释下面两行，并注释掉后面的代码， 可跳过后面token检验
        
        rep.setStatus(HttpServletResponse.SC_OK);
    	chain.doFilter(req, rep);
        
        
//        String servletPath = req.getServletPath();
//        //System.out.println(servletPath);
//        //登录、登出接口直接放行
//        String method = ((HttpServletRequest) req).getMethod();
//        if("OPTIONS".equals(method)){
//        	rep.setStatus(HttpServletResponse.SC_OK);
//        	chain.doFilter(req, rep);
//        }else if ("/login/loginCheck".equals(servletPath)
//        		|| "/personnelConfig/loginCheck".equals(servletPath)){//绕过
//        	rep.setStatus(HttpServletResponse.SC_OK);
//        	chain.doFilter(req, rep);
//        }else{//token校验
//        	 String token = req.getHeader("Authorization");//header方式
//        	 if (null == token || token.isEmpty()) {
//                 System.out.println(MessageUtils.get(CommonI18Constant.com_siti_without_token));
//                 rep.setStatus(JwtUtil.NO_TOKEN);//自定义code
//              } else {
//				try {
//					DecodedJWT decodedJWT = JwtUtil.decrypt(token);
//					if(decodedJWT == null){//解析失败
//						  rep.setStatus(JwtUtil.PARSE_FAILURE);
//	                      System.out.println(MessageUtils.get(CommonI18Constant.com_siti_invalid_token));
//	            	 }else{
//	            		  rep.setStatus(HttpServletResponse.SC_OK);
//	                      chain.doFilter(req, rep);
//	            	 }
//				} catch(TokenExpiredException e){
//					rep.setStatus(JwtUtil.TIME_EXPIRE);
//					e.printStackTrace();
//				} catch(JWTVerificationException e){
//					rep.setStatus(JwtUtil.PARSE_FAILURE);
//					e.printStackTrace();
//				} catch (Exception e) {
//					rep.setStatus(JwtUtil.PARSE_FAILURE);
//					e.printStackTrace();
//				}
//             }
//        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
