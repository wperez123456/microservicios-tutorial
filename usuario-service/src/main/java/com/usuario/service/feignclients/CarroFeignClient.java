package com.usuario.service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.modelos.Carro;

@FeignClient(name = "carro-service", url = "http://localhost:8002")
public interface CarroFeignClient {

	@PostMapping("/carro")
	public Carro save(@RequestBody Carro carro);
}
