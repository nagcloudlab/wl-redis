package com.example.demo_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoServiceApplication {

	//String reqCount = "0";

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@GetMapping("/home")
	public String home() {
		// int count = Integer.parseInt(reqCount);
		// count++;
		// reqCount = Integer.toString(count);
		// return "Hello from Demo Service. Request Count: " + reqCount;
		String reqCount = redisTemplate.opsForValue().get("reqCount"); // read // GET reqCount
		if (reqCount == null) {
			reqCount = "0";
		}
		int count = Integer.parseInt(reqCount);
		count++;
		reqCount = Integer.toString(count);
		redisTemplate.opsForValue().set("reqCount", reqCount); // Write // SET reqCount value
		return "Hello from Demo Service. Request Count: " + reqCount;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceApplication.class, args);
	}

}
