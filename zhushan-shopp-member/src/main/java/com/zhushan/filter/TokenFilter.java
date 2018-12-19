package com.zhushan.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhushan.base.BaseApiService;

@WebFilter(filterName="tokenFilter",urlPatterns={"/user/filter/*"})
@RestController
public class TokenFilter extends BaseApiService implements Filter  {
	
	public void destroy() {
		System.out.println("filter创建了");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	   System.out.println("filter运行了");
       String token = request.getParameter("token");
       if(StringUtils.isEmpty(token)){
    	   PrintWriter out = response.getWriter();
    	   JSONObject responsejson = new JSONObject();
    	   responsejson.put("responseBase", setResultError("Token cannot be empty"));
		   out.println(responsejson);
           out.flush();
           out.close();
       }
       String userId = (String) baseRedisService.getString(token);
       if(StringUtils.isEmpty(userId)){
    	   PrintWriter out = response.getWriter();
    	   JSONObject responsejson = new JSONObject();
    	   responsejson.put("responseBase", setResultError("Token is invalid or has expired"));
           out.println(responsejson);
           out.flush();
           out.close();
	   }
       chain.doFilter(request, response);
	}
       

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("filter完成了初始化");
	}

}
