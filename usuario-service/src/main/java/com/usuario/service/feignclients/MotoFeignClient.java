package com.usuario.service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.modelos.Moto;

//@FeignClient(name = "carro-service", url = "http://localhost:8002") se comenta porque el gateway se encarga del resto
@FeignClient(name = "moto-service")
public interface MotoFeignClient {

	@PostMapping("/moto")
	public Moto save(@RequestBody Moto moto);
}
