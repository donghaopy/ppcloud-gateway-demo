package com.pplive.ppcloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomController {

	protected Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "/ok", method = {RequestMethod.GET})
	public void ok(HttpServletRequest request,
			HttpServletResponse response){
		try {
			response.getOutputStream().write("gateway ok".getBytes("UTF-8"));
			response.getOutputStream().flush();
		}  catch (Exception e) {
			log.error("Exception msg:", e);
		}
	}
}
