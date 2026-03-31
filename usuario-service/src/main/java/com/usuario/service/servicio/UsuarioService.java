package com.usuario.service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entity.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repository.UsuarioRepository;

@Service
public class UsuarioService {

	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CarroFeignClient carroFeignClient;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@SuppressWarnings("unchecked")
	public List<Carro> getCarros(int usuarioId){
		 List<Carro> carros = restTemplate.getForObject("http://carro-service/carro/usuario/" + usuarioId, List.class);
		 return carros;
	}
	
	@SuppressWarnings("unchecked")
	public List<Moto> getMotos(int usuarioId){
		List<Moto> moto = restTemplate.getForObject("http://moto-service/moto/usuario/" + usuarioId, List.class);
		return moto;
	}
	
	public Carro saveCarro(int usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		return carroFeignClient.save(carro);
	}
	
	public List <Usuario> getAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
}
