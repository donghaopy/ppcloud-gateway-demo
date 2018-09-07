package com.pplive.ppcloud.filters;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ErrorFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		//获得出错的attribute
		//String message = (String) ctx.get("error.code");
		//log.error("error messge: "+message);
		//过滤该请求，不对其路由
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		ctx.setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.name());
		return null;
	}

}
