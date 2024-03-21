package com.riggy.example.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

import com.riggy.example.loans.SpringBootRestLoansService17Application;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringBootRestLoansService17Application {

//	@Bean
//	@ServiceConnection
//	OracleContainer oracleFreeContainer() {
//		return new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:latest"));
//	}

	public static void main(String[] args) {
		SpringApplication.from(SpringBootRestLoansService17Application::main).with(TestSpringBootRestLoansService17Application.class).run(args);
	}

}
