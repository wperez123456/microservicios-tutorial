package com.usuario.service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.service.modelos.Moto;

//@FeignClient(name = "moto-service", url = "http://localhost:8002")// se quita la url porque el gateway le proporciola automaticamente la url
@FeignClient(name = "moto-service")
public interface MotoFeignClient {

	@PostMapping("/moto")
	public Moto save(@RequestBody Moto moto);
}
