package com.Project.Project.AppleCouponShop;

import com.Project.Project.AppleCouponShop.Util.Art;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //component scan , configuration scan
public class ProjectPart2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectPart2Application.class, args);
		//ApplicationContext ctx = SpringApplication.run(ProjectPart2Application.class, args);

		System.out.println(Art.LocalHost);
		System.out.println("Just Do It\n");
		System.out.println(Art.Power);
	}
}
