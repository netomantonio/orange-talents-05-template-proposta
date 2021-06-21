package br.com.zup.nossocartao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppPropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppPropostaApplication.class, args);
	}

}
