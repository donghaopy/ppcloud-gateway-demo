package com.pplive.ppcloud.filters;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 
 * @ClassName:     AuthFilter.java
 * @PackageName:   com.pplive.ppcloud.filters
 * @Description:   类功能描述：鉴权过滤器
 * @author         lebrondong
 * @version        V1.0  
 * @Date           2018年9月7日 下午3:57:18
 * @ModyfiedBy
 */
public class AuthFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AuthFilter.class);

	/**
	 * pre：可以在请求被路由之前调用
	 * route：在路由请求时候被调用:比如使用httpclient调用业务服务
	 * post：在route和error过滤器之后被调用
	 * error：处理请求时发生错误时被调用
	 * @return
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		//数值越大，优先级越低
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * 执行顺序：pre -> route -> post <br>
	 * 当抛出异常时会被error执行 ：throw new RuntimeException("version is empty");<br>
	 * @return
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		log.info(String.format("LocalAddr: %s", request.getLocalAddr()));
		log.info(String.format("LocalName: %s", request.getLocalName()));
		log.info(String.format("LocalPort: %s", request.getLocalPort()));

		log.info(String.format("RemoteAddr: %s", request.getRemoteAddr()));
		log.info(String.format("RemoteHost: %s", request.getRemoteHost()));
		log.info(String.format("RemotePort: %s", request.getRemotePort()));
		//过滤转发
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			log.info(String.format("header value: %s", request.getHeader(names.nextElement())));
		}
		String version = request.getHeader("version");
		//错误捕获写入context种
		if (StringUtils.isEmpty(version)) {
			ctx.set("error.code", "30008");
			//过滤该请求，不对其路由
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody("version is empty");
		}
		//添加请求头到下游
		ctx.addZuulRequestHeader("Authorization","");
		return null;
	}

}
