package com.zhushan.zuul;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
@Component
public class TokenFilter extends ZuulFilter {
	
	@Autowired
	protected BaseRedisService baseRedisService;
	
	private static Logger log = LoggerFactory.getLogger(TokenFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	public boolean shouldFilter() {
		return true;
	}

	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		ServletOutputStream out = null;
		try {
			out = ctx.getResponse().getOutputStream();
			log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
			String token = request.getParameter("token");
			if(StringUtils.isEmpty(token)){
		    	out.println("{Token cannot be empty");
			}
			String userId = (String) baseRedisService.getString(token); 
			if(StringUtils.isEmpty(userId)){
		        out.println("Token is invalid or has expired");
			}
		} catch (IOException e) {
		}finally{
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}

}
