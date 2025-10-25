package com.stis.titiktemu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TitiktemuApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitiktemuApplication.class, args);
		System.out.println("\n==============================================");
		System.out.println("🚀 Titik Temu API berhasil dijalankan!");
		System.out.println("📖 Swagger UI: http://localhost:8080/swagger-ui.html");
		System.out.println("📋 API Docs: http://localhost:8080/api-docs");
		System.out.println("==============================================\n");
	}
}