package com.pplive.ppcloud;

import org.junit.Test;
import org.springframework.http.HttpStatus;


public class CommonTest {

	@Test
	public void test1() {
		System.out.println(HttpStatus.INTERNAL_SERVER_ERROR.name());
	}
}
