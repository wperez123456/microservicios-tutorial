package com.usuario.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entity.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
		Usuario usuarioCreado = usuarioService.save(usuario);
		return ResponseEntity.ok(usuarioCreado);
	}
	
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Carro> carros = usuarioService.getCarros(usuarioId);
		return ResponseEntity.ok(carros);
	}
	
	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int usuarioId){
		Usuario usuario = usuarioService.getUsuarioById(usuarioId);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Moto> carros = usuarioService.getMotos(usuarioId);
		return ResponseEntity.ok(carros);
	}
	
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarroUsuario(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro){
		Carro carroCreado = usuarioService.saveCarro(usuarioId,carro);
		return ResponseEntity.ok(carroCreado);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") int usuarioId, RuntimeException exception){
		return new ResponseEntity("El usuario:" + usuarioId + "tiene los carros en el taller", HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<Carro> fallBackSaveCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro, RuntimeException exception){
		return new ResponseEntity("El usuario:" + usuarioId + "no tiene dinero para los carros", HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable("usuarioId") int usuarioId, RuntimeException exception){
		return new ResponseEntity("El usuario:" + usuarioId + "tiene las motos en el taller", HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<Moto> fallBackSaveMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto carro, RuntimeException exception){
		return new ResponseEntity("El usuario:" + usuarioId + "no tiene dinero para las motos", HttpStatus.OK);
	}
}
